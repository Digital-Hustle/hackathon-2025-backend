package ru.ci_trainee.authms.controller.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import ru.ci_trainee.authms.controller.AuthController;
import ru.ci_trainee.authms.dto.request.RegisterUserRq;
import ru.ci_trainee.authms.dto.request.ResetPasswordRq;
import ru.ci_trainee.authms.dto.request.UserLoginRq;
import ru.ci_trainee.authms.mapper.UserMapper;
import ru.ci_trainee.authms.model.JwtData;
import ru.ci_trainee.authms.service.auth.AuthService;
import ru.ci_trainee.authms.service.auth.JwtCookieManager;
import ru.ci_trainee.authms.service.auth.PasswordRecoveryService;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    private final PasswordRecoveryService passwordRecoveryService;
    private final JwtCookieManager cookieManager;
    private final UserMapper userMapper;

    @Override
    public void login(UserLoginRq loginRequest, HttpServletResponse response) {
        var user = userMapper.convert(loginRequest);
        var jwtData = authService.login(user);

        addCookieToResponse(jwtData, response);
    }

    @Override
    public void register(RegisterUserRq registerUserRq) {
        var user = userMapper.convert(registerUserRq);

        authService.register(user);
    }

    @Override
    public void refreshAccess(String refreshToken, HttpServletResponse response) {
        var jwtData = authService.refreshAccess(refreshToken);

        addCookieToResponse(jwtData, response);
    }

    @Override
    public void refreshBothTokens(String refreshToken, HttpServletResponse response) {
        var jwtData = authService.refreshTokens(refreshToken);

        addCookieToResponse(jwtData, response);
    }

    @Override
    public void requestPasswordReset(String email) {
        passwordRecoveryService.sendResetPasswordLink(email);
    }

    @Override
    public void resetPassword(ResetPasswordRq resetPasswordRq) {
        passwordRecoveryService.resetPassword(resetPasswordRq.token(), resetPasswordRq.password());
    }

    private void addCookieToResponse(JwtData jwtData, HttpServletResponse response) {
        var accessCookie = cookieManager.createAccessTokenCookie(jwtData.accessToken());
        var refreshCookie = cookieManager.createRefreshTokenCookie(jwtData.refreshToken());

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }
}
