package rnd.sueta.event_ms.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.entity.Point;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DistanceCalculator {

    private static final int EARTH_RADIUS_KILOMETERS = 6371;

    /**
     * Формула гаверсинусов — это метод сферической тригонометрии для точного расчета кратчайшего расстояния
     * (по большому кругу) между двумя точками на сфере (Земле) по их широте и долготе.
     *
     * <p>
     * <img src="../../../../../../../../../docs/haversinus_formula.png"
     * width="550"
     * alt="Haversinus formula example at project_root/docs/haversinus_formula.png"/>
     *
     * @param startPoint              point with longitude and latitude from which calculate distance
     * @param calculatedDistancePoint point with longitude and latitude to which calculate distance
     * @return BigDecimal distance between points on the map
     */
    public static BigDecimal calculateDistanceWithHaversineFormula(Point startPoint, Point calculatedDistancePoint) {
        double startLatitudeInRadians = Math.toRadians(startPoint.getLatitude().doubleValue());
        double startLongitudeInRadians = Math.toRadians(startPoint.getLongitude().doubleValue());
        double destinationLatitudeInRadians = Math.toRadians(calculatedDistancePoint.getLatitude().doubleValue());
        double destinationLongitudeInRadians = Math.toRadians(calculatedDistancePoint.getLongitude().doubleValue());

        double deltaLat = destinationLatitudeInRadians - startLatitudeInRadians;
        double deltaLon = destinationLongitudeInRadians - startLongitudeInRadians;

        double startLatitudeCosinusValue = Math.cos(startLatitudeInRadians);
        double destinationLatitudeCosinusValue = Math.cos(destinationLatitudeInRadians);

        double haversinusResult = haversinus(deltaLat) + startLatitudeCosinusValue * destinationLatitudeCosinusValue * haversinus(deltaLon);

        double distance =
                2 * EARTH_RADIUS_KILOMETERS * Math.atan2(Math.sqrt(haversinusResult), Math.sqrt(1 - haversinusResult));

        return BigDecimal.valueOf(distance);
    }

    private static double haversinus(double delta) {
        return Math.pow(Math.sin(delta / 2), 2);
    }

    public static Map<EventWithPlace, BigDecimal> getDistanceMap(Point startRoutePoint, List<EventWithPlace> places) {
        return places.stream()
                .collect(Collectors.toMap(
                        place -> place, place -> {
                            Point firstPlaceCoordinate = PointFactory.extractPointFromPlace(place);
                            return calculateDistanceWithHaversineFormula(startRoutePoint, firstPlaceCoordinate);
                        }
                ));
    }
}
