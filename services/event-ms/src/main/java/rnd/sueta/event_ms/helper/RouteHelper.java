package rnd.sueta.event_ms.helper;

import org.springframework.stereotype.Component;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.RouteEvents;
import rnd.sueta.event_ms.model.RoutePlaces;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public final class RouteHelper {

    public List<RoutePlaces> mapRoutePlaces(UUID routeId, List<EventWithPlace> placesInRange) {
        return IntStream.range(0, placesInRange.size())
                .mapToObj(index -> {
                    int positionIndex = index + 1;
                    EventWithPlace place = placesInRange.get(index);

                    return RoutePlaces.builder()
                            .id(UUID.randomUUID())
                            .routeId(routeId)
                            .placeId(place.placeId())
                            .position(positionIndex)
                            .build();
                })
                .toList();
    }

    public List<RouteEvents> mapRouteEvents(UUID routeId, List<EventWithPlace> placesInRange) {
        return placesInRange.stream()
                .map(eventWithPlace ->
                        RouteEvents.builder()
                                .id(UUID.randomUUID())
                                .routeId(routeId)
                                .eventId(eventWithPlace.eventId())
                                .build()
                )
                .toList();
    }

    public List<EventWithPlace> getUniqueRoutePlaces(List<EventWithPlace> places) {
        return places.stream()
                .collect(Collectors.toMap(
                        EventWithPlace::placeId,
                        Function.identity(),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ))
                .values()
                .stream()
                .toList();
    }
}
