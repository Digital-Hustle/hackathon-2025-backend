package rnd.sueta.event_ms.model;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record RouteWithEvents(

        UUID id,

        UUID profileId,

        List<EventWithPlace> events
) {
}
