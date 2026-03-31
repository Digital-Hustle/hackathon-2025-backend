package rnd.sueta.event_ms.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import rnd.sueta.event_ms.annotations.ValidPhones;
import rnd.sueta.event_ms.annotations.ValidUrl;
import rnd.sueta.event_ms.constants.PlaceConstants;
import rnd.sueta.event_ms.constants.ValidationErrorMessages;
import rnd.sueta.event_ms.enums.PlaceType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record CreatePlaceRq(

        @NotBlank(message = "title " + ValidationErrorMessages.IS_REQUIRED)
        @Size(max = PlaceConstants.MAX_TITLE_LENGTH, message = "title " + ValidationErrorMessages.STRING_IS_TOO_LONG)
        String title,

        @NotBlank(message = "address " + ValidationErrorMessages.IS_REQUIRED)
        @Size(max = PlaceConstants.MAX_ADDRESS_LENGTH, message = "address " + ValidationErrorMessages.STRING_IS_TOO_LONG)
        String address,

        @NotNull(message = "eventType " + ValidationErrorMessages.IS_REQUIRED)
        PlaceType type,

        @NotNull(message = "ownerId " + ValidationErrorMessages.IS_REQUIRED)
        UUID ownerId,

        @NotNull(message = "latitude " + ValidationErrorMessages.IS_REQUIRED)
        @Digits(
                integer = PlaceConstants.COORDINATE_PRECISION,
                fraction = PlaceConstants.COORDINATE_SCALE,
                message = "latitude " + ValidationErrorMessages.BIG_DECIMAL_VALID_RANGE)
        @DecimalMin(
                value = PlaceConstants.MIN_LATITUDE_VALUE,
                message = ValidationErrorMessages.MIN_LATITUDE_VALUE)
        @DecimalMax(
                value = PlaceConstants.MAX_LATITUDE_VALUE,
                message = ValidationErrorMessages.MAX_LATITUDE_VALUE
        )
        BigDecimal latitude,

        @NotNull(message = "longitude " + ValidationErrorMessages.IS_REQUIRED)
        @Digits(
                integer = PlaceConstants.COORDINATE_PRECISION,
                fraction = PlaceConstants.COORDINATE_SCALE,
                message = "longitude " + ValidationErrorMessages.BIG_DECIMAL_VALID_RANGE)
        @DecimalMin(
                value = PlaceConstants.MIN_LONGITUDE_VALUE,
                message = ValidationErrorMessages.MIN_LONGITUDE_VALUE)
        @DecimalMax(
                value = PlaceConstants.MAX_LONGITUDE_VALUE,
                message = ValidationErrorMessages.MAX_LONGITUDE_VALUE
        )
        BigDecimal longitude,

        @ValidPhones(message = ValidationErrorMessages.INVALID_PHONE_FORMAT)
        List<String> mobileNumbers,

        @Email(message = ValidationErrorMessages.INVALID_EMAIL_FORMAT)
        String email,

        @ValidUrl(message = ValidationErrorMessages.INVALID_URL_FORMAT)
        String websiteUrl,

        Map<String, String> social
) {
}
