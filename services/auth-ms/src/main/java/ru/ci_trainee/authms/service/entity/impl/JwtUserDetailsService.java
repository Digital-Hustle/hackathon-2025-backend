package ru.ci_trainee.authms.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.ci_trainee.authms.model.JwtDetails;
import ru.ci_trainee.authms.model.entity.User;
import ru.ci_trainee.authms.service.entity.UserService;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.getUser(email);

        return JwtDetails.builder()
                .id(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
