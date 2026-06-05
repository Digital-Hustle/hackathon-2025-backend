package ru.ci_trainee.authms.service.auth;

import org.springframework.security.core.Authentication;
import ru.ci_trainee.authms.model.JwtData;

import java.util.UUID;

public interface JwtTokenProvider {

    String createAccessToken(UUID userId, String email);

    String createRefreshToken(UUID userId, String email);

    JwtData refreshAccessToken(String refreshToken);

    JwtData refreshUserTokens(String refreshToken);

    boolean isValid(String token);

    Authentication getAuthentication(String token);
}
