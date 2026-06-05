package rnd.sueta.model;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record RouteWithPlaces(

        UUID id,

        UUID profileId,

        List<PlaceWithCoordinates> places
) {
}
