package rnd.sueta.model;

import lombok.Builder;
import rnd.sueta.enums.EventType;

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
