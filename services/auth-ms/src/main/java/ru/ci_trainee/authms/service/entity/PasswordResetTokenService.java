package ru.ci_trainee.authms.service.entity;

import ru.ci_trainee.authms.model.entity.PasswordResetToken;

import java.util.UUID;

public interface PasswordResetTokenService {

    PasswordResetToken getByToken(String token);

    PasswordResetToken save(UUID userId);

    PasswordResetToken update(PasswordResetToken resetToken);

    void deleteAllExpiredTokens();

    void deleteAllUserTokens(UUID userId);
}
