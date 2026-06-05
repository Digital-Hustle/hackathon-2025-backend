package rnd.sueta.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PointsErrorMessages {
    public static final String POINT_OUT_OF_CITY = "Point is out of city area";
    public static final String ROUTE_POINTS_OUT_OF_CITY = "Can't generate route from points located out of city";
}
