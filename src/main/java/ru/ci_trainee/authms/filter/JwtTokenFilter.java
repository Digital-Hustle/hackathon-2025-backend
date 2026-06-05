package ru.ci_trainee.authms.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.ci_trainee.authms.service.auth.JwtCookieManager;
import ru.ci_trainee.authms.service.auth.JwtTokenProvider;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtCookieManager cookieManager;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        String bearerToken = cookieManager.getAccessTokenFromRequest(request);

        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            bearerToken = bearerToken.substring(BEARER_PREFIX.length());
        }

        try {
            if (bearerToken != null && jwtTokenProvider.isValid(bearerToken)) {
                Authentication auth = jwtTokenProvider.getAuthentication(bearerToken);

                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (ExpiredJwtException expiredTokenException) {
            log.warn("Expired JWT token for user: {}", expiredTokenException.getClaims().getSubject());
        } catch (Exception ignored) {
            return;
        }

        filterChain.doFilter(request, response);
    }
}
