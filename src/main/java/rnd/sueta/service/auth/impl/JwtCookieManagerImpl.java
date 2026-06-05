package rnd.sueta.service.auth.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import ru.ci_trainee.authms.config.properties.JwtProperties;
import ru.ci_trainee.authms.service.auth.JwtCookieManager;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class JwtCookieManagerImpl implements JwtCookieManager {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String STRICT_SAME_SITE = "Strict";
    private static final String ALL_PATHS = "/";

    private final JwtProperties jwtProperties;

    @Override
    public ResponseCookie createAccessTokenCookie(String accessToken) {
        return ResponseCookie.from(ACCESS_TOKEN, accessToken)
                .httpOnly(true)
                .path(ALL_PATHS)
                .sameSite(STRICT_SAME_SITE)
                .maxAge(Duration.ofMinutes(jwtProperties.getAccessDuration()))
                .build();
    }

    @Override
    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .path(ALL_PATHS)
                .sameSite(STRICT_SAME_SITE)
                .maxAge(Duration.ofDays(jwtProperties.getRefreshDuration()))
                .build();
    }

    @Override
    public String getAccessTokenFromRequest(HttpServletRequest request) {
        return getCookieValue(request, ACCESS_TOKEN);
    }

    @Nullable
    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
