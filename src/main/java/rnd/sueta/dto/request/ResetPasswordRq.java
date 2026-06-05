package rnd.sueta.dto.request;

import jakarta.validation.constraints.NotBlank;
import ru.ci_trainee.authms.constants.ErrorMessages;

public record ResetPasswordRq(

        @NotBlank(message = "password" + ErrorMessages.IS_REQUIRED)
        String token,

        @NotBlank(message = "password" + ErrorMessages.IS_REQUIRED)
        String password
) {
}
