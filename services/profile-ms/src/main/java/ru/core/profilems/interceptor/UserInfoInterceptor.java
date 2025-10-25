package ru.core.profilems.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.core.profilems.constants.CustomHeaders;
import ru.core.profilems.dto.CurrentUser;
import ru.core.profilems.dto.Role;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserInfoInterceptor implements HandlerInterceptor {
    private final CurrentUser currentUser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        currentUser.setUserId(request.getHeader(CustomHeaders.USER_ID));
        currentUser.setUsername(request.getHeader(CustomHeaders.USERNAME));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        currentUser.clear();
    }
}
