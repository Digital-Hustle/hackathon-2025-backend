package ru.ci_trainee.authms.service.auth;

import ru.ci_trainee.authms.model.JwtData;
import ru.ci_trainee.authms.model.UserWithCredentials;
import ru.ci_trainee.authms.model.entity.User;

public interface AuthService {

    User register(UserWithCredentials userWithCredentials);

    JwtData login(User user);

    JwtData refreshAccess(String refreshToken);

    JwtData refreshTokens(String refreshToken);
}
