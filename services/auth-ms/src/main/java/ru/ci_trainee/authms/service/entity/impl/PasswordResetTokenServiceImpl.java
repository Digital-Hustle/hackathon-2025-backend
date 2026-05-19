package ru.ci_trainee.authms.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ci_trainee.authms.constants.TokenConstants;
import ru.ci_trainee.authms.model.entity.PasswordResetToken;
import ru.ci_trainee.authms.repository.PasswordResetTokenRepository;
import ru.ci_trainee.authms.service.entity.PasswordResetTokenService;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepository;

    @Override
    public PasswordResetToken getByToken(String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public PasswordResetToken save(UUID userId) {
        String tokenValue = generateToken();
        OffsetDateTime expireTime = OffsetDateTime.now()
                .plusMinutes(TokenConstants.VALIDITY_MINUTES);

        PasswordResetToken token = PasswordResetToken.builder()
                .id(UUID.randomUUID())
                .token(tokenValue)
                .userId(userId)
                .expiryDate(expireTime)
                .used(false)
                .build();

        return tokenRepository.save(token);
    }

    @Override
    public PasswordResetToken update(PasswordResetToken resetToken) {
        return tokenRepository.save(resetToken);
    }

    @Override
    public void deleteAllExpiredTokens() {
        tokenRepository.deleteAllByExpiryDateBefore(LocalDateTime.now());
    }

    @Override
    public void deleteAllUserTokens(UUID userId) {
        tokenRepository.deleteByUserId(userId);
    }

    private String generateToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(TokenConstants.BASE_LENGTH);

        for (int index = 0; index < TokenConstants.BASE_LENGTH - TokenConstants.BASE_PREFIX.length(); index++) {
            int randomIndex = random.nextInt(TokenConstants.KEY_POOL.length());
            token.append(TokenConstants.KEY_POOL.charAt(randomIndex));
        }

        return TokenConstants.BASE_PREFIX + token;
    }
}
