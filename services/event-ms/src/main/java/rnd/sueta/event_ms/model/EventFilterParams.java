package rnd.sueta.event_ms.model;

import lombok.Builder;
import rnd.sueta.event_ms.enums.EventType;

import java.time.OffsetDateTime;
import java.util.List;

@Builder(toBuilder = true)
public record EventFilterParams(

        int page,

        int size,

        OffsetDateTime date,

        List<EventType> categories
) {
}
