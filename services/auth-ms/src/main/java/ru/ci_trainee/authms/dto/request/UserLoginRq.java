package ru.ci_trainee.authms.dto.request;

import jakarta.validation.constraints.NotBlank;
import ru.ci_trainee.authms.constants.ErrorMessages;

public record UserLoginRq(

        @NotBlank(message = "email" + ErrorMessages.IS_REQUIRED)
        String email,

        @NotBlank(message = "password" + ErrorMessages.IS_REQUIRED)
        String password
) {
}
