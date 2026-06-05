package rnd.sueta.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rnd.sueta.model.EventWithPlace;
import rnd.sueta.model.entity.Point;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PointFactory {

    public static Point extractPointFromPlace(EventWithPlace place) {
        return Point.builder()
                .latitude(place.latitude())
                .longitude(place.longitude())
                .build();
    }
}
