package ru.ci_trainee.authms.service.auth;

public interface PasswordRecoveryService {

    void sendResetPasswordLink(String email);

    void resetPassword(String token, String newPassword);
}
