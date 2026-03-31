package rnd.sueta.event_ms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import rnd.sueta.event_ms.annotations.ValidPhones;
import rnd.sueta.event_ms.annotations.ValidUrl;
import rnd.sueta.event_ms.constants.PlaceConstants;
import rnd.sueta.event_ms.constants.ValidationErrorMessages;
import rnd.sueta.event_ms.enums.PlaceType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record UpdatePlaceRq(

        @NotBlank(message = "title " + ValidationErrorMessages.IS_REQUIRED)
        @Size(max = PlaceConstants.MAX_TITLE_LENGTH, message = "title " + ValidationErrorMessages.STRING_IS_TOO_LONG)
        String title,

        @NotNull(message = "eventType " + ValidationErrorMessages.IS_REQUIRED)
        PlaceType type,

        @NotNull(message = "ownerId " + ValidationErrorMessages.IS_REQUIRED)
        UUID ownerId,

        @ValidPhones(message = ValidationErrorMessages.INVALID_PHONE_FORMAT)
        List<String> mobileNumbers,

        @Email(message = ValidationErrorMessages.INVALID_EMAIL_FORMAT)
        String email,

        @ValidUrl(message = ValidationErrorMessages.INVALID_URL_FORMAT)
        String websiteUrl,

        Map<String, String> social
) {
}
