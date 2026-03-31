package rnd.sueta.event_ms.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.entity.Point;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PointFactory {

    public static Point extractPointFromPlace(EventWithPlace place) {
        return Point.builder()
                .latitude(place.latitude())
                .longitude(place.longitude())
                .build();
    }
}
