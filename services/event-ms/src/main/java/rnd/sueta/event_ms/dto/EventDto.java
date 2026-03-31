package rnd.sueta.event_ms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import rnd.sueta.event_ms.enums.EventType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EventDto(

        UUID id,

        UUID organizerId,

        String title,

        EventType type,

        BigDecimal rating,

        OffsetDateTime eventStart,

        OffsetDateTime eventEnd,

        BigDecimal price,

        Integer ageRestriction,

        UUID placeId
) {
}
