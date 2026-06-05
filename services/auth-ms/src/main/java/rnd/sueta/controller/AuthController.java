package rnd.sueta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ci_trainee.authms.constants.ApiVersionConstants;
import ru.ci_trainee.authms.constants.UrlPaths;
import ru.ci_trainee.authms.dto.request.RegisterUserRq;
import ru.ci_trainee.authms.dto.request.ResetPasswordRq;
import ru.ci_trainee.authms.dto.request.UserLoginRq;

@RequestMapping(UrlPaths.AUTH)
public interface AuthController {

    @Operation(
            summary = "Login user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad credentials"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PostMapping(value = UrlPaths.LOGIN, version = ApiVersionConstants.VERSION_1)
    void login(@RequestBody @Valid UserLoginRq loginRequest, HttpServletResponse response);

    @Operation(
            summary = "Register user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "New user created"),
                    @ApiResponse(responseCode = "400", description = "Validation failed")
            }
    )
    @PostMapping(value = UrlPaths.REGISTER, version = ApiVersionConstants.VERSION_1)
    @ResponseStatus(HttpStatus.CREATED)
    void register(@RequestBody @Valid RegisterUserRq registerUserRq);

    @Operation(
            summary = "Refresh access token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "New tokens")
            }
    )
    @PostMapping(value = UrlPaths.TOKENS_ACCESS, version = ApiVersionConstants.VERSION_1)
    void refreshAccess(@RequestBody String refreshToken, HttpServletResponse response);

    @Operation(
            summary = "Refresh both access and refresh tokens",
            responses = {
                    @ApiResponse(responseCode = "200", description = "New tokens")
            }
    )
    @PostMapping(value = UrlPaths.TOKENS_BOTH, version = ApiVersionConstants.VERSION_1)
    void refreshBothTokens(@RequestBody String refreshToken, HttpServletResponse response);

    @Operation(
            summary = "Request password reset",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Send email for password reset")
            }
    )
    @PostMapping(value = UrlPaths.PASSWORD_RESET, version = ApiVersionConstants.VERSION_1)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void requestPasswordReset(@RequestBody String email);

    @Operation(
            summary = "Reset password",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Reset password")
            }
    )
    @PostMapping(value = UrlPaths.PASSWORD_RESET_CONFIRMATION, version = ApiVersionConstants.VERSION_1)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void resetPassword(@RequestBody ResetPasswordRq resetPasswordRq);
}
