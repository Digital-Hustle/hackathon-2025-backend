package rnd.sueta.model;

import lombok.Builder;
import rnd.sueta.enums.EventType;
import rnd.sueta.model.entity.Point;

import java.math.BigDecimal;
import java.util.List;

@Builder(toBuilder = true)
public record RouteGenerationParams(

        Point startPoint,

        Point endPoint,

        BigDecimal budget,

        List<EventType> categories
) {
}
