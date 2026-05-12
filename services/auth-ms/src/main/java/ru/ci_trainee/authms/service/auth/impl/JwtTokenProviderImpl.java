package ru.ci_trainee.authms.service.auth.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.ci_trainee.authms.config.properties.JwtProperties;
import ru.ci_trainee.authms.constants.ErrorMessages;
import ru.ci_trainee.authms.exception.exception.InvalidTokenException;
import ru.ci_trainee.authms.model.JwtData;
import ru.ci_trainee.authms.service.auth.JwtTokenProvider;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenProviderImpl implements JwtTokenProvider {

    private static final String ID = "id";

    private SecretKey secretKey;
    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    @Override
    public String createAccessToken(UUID userId, String email) {
        Claims claims = Jwts.claims()
                .subject(email)
                .add(ID, userId)
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getAccessDuration(), ChronoUnit.MINUTES);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String createRefreshToken(UUID userId, String email) {
        Claims claims = Jwts.claims()
                .subject(email)
                .add(ID, userId)
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getRefreshDuration(), ChronoUnit.DAYS);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public JwtData refreshAccessToken(String refreshToken) {
        if (!isValid(refreshToken)) {
            throw new InvalidTokenException(ErrorMessages.TOKEN_EXPIRED);
        }

        UUID userId = getId(refreshToken);
        String email = getEmail(refreshToken);

        return JwtData.builder()
                .id(userId)
                .email(email)
                .accessToken(createAccessToken(userId, email))
                .build();
    }

    @Override
    public JwtData refreshUserTokens(String refreshToken) {
        if (!isValid(refreshToken)) {
            throw new InvalidTokenException(ErrorMessages.TOKEN_EXPIRED);
        }

        UUID userId = getId(refreshToken);
        String email = getEmail(refreshToken);

        return JwtData.builder()
                .id(userId)
                .email(email)
                .accessToken(createAccessToken(userId, email))
                .accessToken(createRefreshToken(userId, email))
                .build();
    }

    @Override
    public boolean isValid(String token) {
        Jws<Claims> claims = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

        return claims
                .getPayload()
                .getExpiration()
                .after(new Date());
    }

    @Override
    public Authentication getAuthentication(String token) {
        String email = getEmail(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(
                userDetails, StringUtils.EMPTY, userDetails.getAuthorities()
        );
    }

    private UUID getId(String token) {
        return UUID.fromString(getClaims(token).get(ID, String.class));
    }

    private String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
