package rnd.sueta.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import rnd.sueta.constants.EventConstants;
import rnd.sueta.constants.ValidationErrorMessages;
import rnd.sueta.dto.PointDto;
import rnd.sueta.enums.EventType;

import java.math.BigDecimal;
import java.util.List;

public record CreateRouteRq(

        @Valid
        PointDto startPoint,

        @Valid
        PointDto endPoint,

        @NotNull(message = "budget " + ValidationErrorMessages.IS_REQUIRED)
        @DecimalMin(
                value = EventConstants.MIN_PRICE_VALUE,
                message = "budget" + ValidationErrorMessages.POSITIVE_VALUE)
        @Digits(
                integer = EventConstants.PRICE_PRECISION,
                fraction = EventConstants.PRICE_SCALE,
                message = ValidationErrorMessages.BIG_DECIMAL_VALID_RANGE)
        BigDecimal budget,

        List<EventType> categories
) {
}
