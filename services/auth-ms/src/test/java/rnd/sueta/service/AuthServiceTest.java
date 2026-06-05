package rnd.sueta.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import ru.ci_trainee.authms.model.JwtData;
import ru.ci_trainee.authms.model.entity.User;
import ru.ci_trainee.authms.service.auth.AuthService;
import ru.ci_trainee.authms.service.auth.JwtTokenProvider;
import ru.ci_trainee.authms.service.entity.UserService;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    @Transactional
    void login_ShouldReturnJwtResponse_WhenCredentialsValid() {
        // Arrange
        var userId = UUID.randomUUID();
        var user = User.builder()
                .id(userId)
                .email("testuser@mail.ru")
                .password("encodedPass")
                .isActive(true)
                .build();

        var request = User.builder()
                .email("testuser")
                .password("password")
                .build();
        var expectedResponse = JwtData.builder()
                .id(userId)
                .email("testuser")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        when(userService.getUser("testuser")).thenReturn(user);
        when(jwtTokenProvider.createAccessToken(user.getId(), user.getEmail())).thenReturn("accessToken");
        when(jwtTokenProvider.createRefreshToken(userId, "testuser")).thenReturn("refreshToken");

        JwtData actualResponse = authService.login(request);

        // Assert
        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("testuser", "password")
        );
        verify(userService).updateLastLogin(userId);
    }

    @Test
    void login_ShouldThrowEntityNotFoundException_WhenUserInactive() {
        var inactiveUser = User.builder()
                .email("testuser@mail.ru")
                .isActive(false)
                .build();

        when(userService.getUser("inactive")).thenReturn(inactiveUser);

        var user = User.builder()
                .email("testuser")
                .password("password")
                .build();

        assertThatThrownBy(() -> authService.login(user))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void login_ShouldThrowBadCredentials_WhenAuthenticationFails() {
        // Arrange
        User user = User.builder()
                .email("testuser@mail.ru")
                .isActive(true)
                .build();

        when(userService.getUser("testuser")).thenReturn(user);
        doThrow(new BadCredentialsException("Invalid credentials"))
                .when(authenticationManager).authenticate(any());

        var userCred = User.builder()
                .email("testuser")
                .password("password")
                .build();


        // Act & Assert
        assertThatThrownBy(() -> authService.login(userCred))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid credentials");
    }


    @Test
    void refresh_ShouldReturnNewTokens_WhenRefreshTokenValid() {
        // Arrange
        String refreshToken = "valid.refresh.token";
        JwtData expectedResponse = JwtData.builder()
                .accessToken("newAccess")
                .refreshToken("newRefresh")
                .build();

        when(jwtTokenProvider.refreshUserTokens(refreshToken)).thenReturn(expectedResponse);

        // Act
        JwtData actualResponse = authService.refreshTokens(refreshToken);

        // Assert
        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(jwtTokenProvider).refreshUserTokens(refreshToken);
    }
}
