package ru.ci_trainee.authms.service.auth.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ci_trainee.authms.constants.ErrorMessages;
import ru.ci_trainee.authms.constants.ResourceLocations;
import ru.ci_trainee.authms.constants.UrlPaths;
import ru.ci_trainee.authms.exception.exception.ExpiredTokenException;
import ru.ci_trainee.authms.exception.exception.ResourceLoadingException;
import ru.ci_trainee.authms.exception.exception.TokenAlreadyUsedException;
import ru.ci_trainee.authms.gateway.MailGateway;
import ru.ci_trainee.authms.model.entity.PasswordResetToken;
import ru.ci_trainee.authms.model.entity.User;
import ru.ci_trainee.authms.service.auth.PasswordRecoveryService;
import ru.ci_trainee.authms.service.entity.PasswordResetTokenService;
import ru.ci_trainee.authms.service.entity.UserService;
import ru.digital_hustle.exceptions_starter.constant.ExceptionConstants;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {

    private static final String RESET_PATH = UrlPaths.AUTH + UrlPaths.PASSWORD_RESET + "?token=";

    @Value("${app.paths.url.reset-password}")
    private String baseUrl;

    private String resetPasswordTemplate;

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final MailGateway mailGatewayImpl;
    private final ResourceLoader resourceLoader;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initTemplates() {
        resetPasswordTemplate = loadRessetTemplate();
    }

    @Transactional
    @Override
    public void sendResetPasswordLink(String email) {
        User user = userService.getUser(email);
        passwordResetTokenService.deleteAllUserTokens(user.getId());

        String token = passwordResetTokenService.save(user.getId()).getToken();
        String resetUrl = baseUrl + RESET_PATH + token;
        String html = resetPasswordTemplate.replace("{{linkWithToken}}", resetUrl);

        mailGatewayImpl.send(email, html);
    }

    @Transactional
    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenService.getByToken(token);

        if (resetToken.isUsed()) {
            log.warn(ExceptionConstants.LOG_MESSAGE, "Token has already been used");
            throw new TokenAlreadyUsedException(ErrorMessages.TOKEN_USED);
        }

        if (resetToken.getExpiryDate().isBefore(OffsetDateTime.now())) {
            log.warn(ExceptionConstants.LOG_MESSAGE, "Token is expired");
            throw new ExpiredTokenException(ErrorMessages.TOKEN_EXPIRED);
        }

        User user = userService.getUser(resetToken.getUserId());
        userService.update(user.toBuilder()
                .password(passwordEncoder.encode(newPassword))
                .build()
        );

        passwordResetTokenService.update(resetToken.toBuilder()
                .used(true)
                .build()
        );
    }

    private String loadRessetTemplate() {
        Resource resource = resourceLoader.getResource(ResourceLocations.RESSET_PASSWORD_TEMPLATE);

        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            log.error(ExceptionConstants.LOG_MESSAGE, "Failed to load resset password template. " + exception.getMessage());
            throw new ResourceLoadingException("Template loading failed");
        }
    }
}
