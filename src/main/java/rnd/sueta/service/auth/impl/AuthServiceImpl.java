package rnd.sueta.service.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ci_trainee.authms.exception.exception.EntityAlreadyExistsException;
import ru.ci_trainee.authms.exception.exception.PasswordsDoNotMatchException;
import ru.ci_trainee.authms.model.JwtData;
import ru.ci_trainee.authms.model.UserWithCredentials;
import ru.ci_trainee.authms.model.entity.User;
import ru.ci_trainee.authms.service.auth.AuthService;
import ru.ci_trainee.authms.service.auth.JwtTokenProvider;
import ru.ci_trainee.authms.service.entity.UserService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public User register(UserWithCredentials userWithCredentials) {
        if (!userWithCredentials.password().equals(userWithCredentials.passwordConfirmation())) {
            throw new PasswordsDoNotMatchException("Passwords don't match");
        }

        var user = User.builder()
                .email(userWithCredentials.email())
                .password(passwordEncoder.encode(userWithCredentials.password()))
                .build();

        return userService.save(user);
    }

    @Override
    public JwtData login(User user) {
        var dbUser = userService.getUser(user.getEmail());
        if (!dbUser.getIsActive()) {
            throw new EntityAlreadyExistsException("User with such email already exists");
        }

        var authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()
        );

        userService.updateLastLogin(dbUser.getId());
        authenticationManager.authenticate(authentication);

        return JwtData.builder()
                .id(dbUser.getId())
                .email(dbUser.getEmail())
                .accessToken(jwtTokenProvider.createAccessToken(dbUser.getId(), dbUser.getEmail()))
                .refreshToken(jwtTokenProvider.createRefreshToken(dbUser.getId(), dbUser.getEmail()))
                .build();
    }

    @Override
    public JwtData refreshAccess(String refreshToken) {

        return jwtTokenProvider.refreshAccessToken(refreshToken);
    }

    @Override
    public JwtData refreshTokens(String refreshToken) {

        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
