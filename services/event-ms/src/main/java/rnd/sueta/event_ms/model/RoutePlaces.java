package rnd.sueta.event_ms.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record RoutePlaces(

        UUID id,

        UUID routeId,

        UUID placeId,

        Integer position
) {
}
