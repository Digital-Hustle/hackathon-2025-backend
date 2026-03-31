package rnd.sueta.event_ms.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import rnd.sueta.event_ms.config.ThreadLocalMap;
import rnd.sueta.event_ms.constants.ContextKeys;
import rnd.sueta.event_ms.constants.CustomHeaders;
import rnd.sueta.event_ms.constants.ErrorMessages;

import java.io.IOException;

@Slf4j
@Component
public class UserIdentificationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String userId = request.getHeader(CustomHeaders.USER_ID);
            String username = request.getHeader(CustomHeaders.USERNAME);

            if (StringUtils.isBlank(userId) || StringUtils.isBlank(username)) {
                log.warn("Invalid user identifiers. {}='{}', {}='{}'",
                        CustomHeaders.USER_ID, userId,
                        CustomHeaders.USERNAME, username
                );

                response.sendError(HttpStatus.BAD_REQUEST.value(), ErrorMessages.PROFILE_UNDEFINED);
                return;
            }

            log.debug("Request from {} user with ID {}", username, userId);

            ThreadLocalMap.put(ContextKeys.PROFILE_ID, userId);
            ThreadLocalMap.put(ContextKeys.USERNAME, username);

            filterChain.doFilter(request, response);
        } finally {
            ThreadLocalMap.clear();
        }
    }
}
