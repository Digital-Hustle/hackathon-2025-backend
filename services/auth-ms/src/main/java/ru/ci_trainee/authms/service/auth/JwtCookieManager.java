package ru.ci_trainee.authms.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

public interface JwtCookieManager {

    ResponseCookie createAccessTokenCookie(String accessToken);

    ResponseCookie createRefreshTokenCookie(String refreshToken);

    String getAccessTokenFromRequest(HttpServletRequest request);
}
