package rnd.sueta.event_ms.service;


import rnd.sueta.event_ms.enums.Category;
import rnd.sueta.event_ms.model.Place;

import java.math.BigDecimal;
import java.util.List;

// ручка
// select event + place о категориям и
public interface PlaceService {
    List<Place> getAllByCategories(
            List<BigDecimal> rangeLat,
            List<BigDecimal> rangeLon,
            List<Category> categories
    );
}
