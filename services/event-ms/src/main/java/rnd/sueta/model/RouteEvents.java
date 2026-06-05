package rnd.sueta.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record RouteEvents(

        UUID id,

        UUID routeId,

        UUID eventId
) {
}
