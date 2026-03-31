package rnd.sueta.event_ms.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rnd.sueta.event_ms.config.properties.BorderProperties;
import rnd.sueta.event_ms.constants.PointsErrorMessages;
import rnd.sueta.event_ms.exception.custom.OutOfCityPointException;
import rnd.sueta.event_ms.model.entity.Point;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public final class PointValidator {

    private final BorderProperties borders;

    public void checkPointWithinCityArea(Point point) {
        if (isPointOutOfCityArea(point)) {
            throw new OutOfCityPointException(PointsErrorMessages.POINT_OUT_OF_CITY);
        }
    }

    public void checkRoutePointsWithinCityArea(Point startRoutePoint, Point endRoutePoint) {
        if (isPointOutOfCityArea(startRoutePoint) || isPointOutOfCityArea(endRoutePoint)) {
            throw new OutOfCityPointException(PointsErrorMessages.ROUTE_POINTS_OUT_OF_CITY);
        }
    }

    private boolean isPointInCityArea(Point point) {
        BigDecimal minLongitude = borders.getBottomLeftCorner().getLongitude();
        BigDecimal maxLongitude = borders.getBottomRightCorner().getLongitude();
        BigDecimal minLatitude = borders.getBottomLeftCorner().getLatitude();
        BigDecimal maxLatitude = borders.getTopLeftCorner().getLatitude();

        return point.getLongitude().compareTo(minLongitude) >= 0 && point.getLongitude().compareTo(maxLongitude) <= 0
                && point.getLatitude().compareTo(minLatitude) >= 0 && point.getLatitude().compareTo(maxLatitude) <= 0;
    }

    private boolean isPointOutOfCityArea(Point point) {
        return !isPointInCityArea(point);
    }
}
