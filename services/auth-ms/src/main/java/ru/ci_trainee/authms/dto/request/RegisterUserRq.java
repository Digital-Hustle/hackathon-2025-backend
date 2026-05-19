package ru.ci_trainee.authms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import ru.ci_trainee.authms.constants.ErrorMessages;

public record RegisterUserRq(

        @Email
        @NotBlank(message = "email" + ErrorMessages.IS_REQUIRED)
        String email,

        @NotBlank(message = "password" + ErrorMessages.IS_REQUIRED)
        String password,

        @NotBlank(message = "passwordConfirmation" + ErrorMessages.IS_REQUIRED)
        String passwordConfirmation
) {
}
