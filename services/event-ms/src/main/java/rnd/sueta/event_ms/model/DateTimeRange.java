package rnd.sueta.event_ms.model;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder(toBuilder = true)
public record DateTimeRange(

        OffsetDateTime start,

        OffsetDateTime end
) {
}
