package rnd.sueta.event_ms.dto.params;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import rnd.sueta.event_ms.constants.RequestParamsValidationConstants;
import rnd.sueta.event_ms.constants.ValidationErrorMessages;
import rnd.sueta.event_ms.enums.EventType;

import java.time.OffsetDateTime;
import java.util.List;

public record EventsFilter(

        @NotNull(message = "date " + ValidationErrorMessages.IS_REQUIRED)
        OffsetDateTime date,

        @Size(max = RequestParamsValidationConstants.MAX_CATEGORIES_QUANTITY)
        List<EventType> categories
) {
}
