package rnd.sueta.event_ms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rnd.sueta.event_ms.enums.Category;
import rnd.sueta.event_ms.model.Place;
import rnd.sueta.event_ms.repository.PlaceRepository;
import rnd.sueta.event_ms.service.PlaceService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;

    @Override
    public List<Place> getAllByCategories(List<BigDecimal> rangeLat, List<BigDecimal> rangeLon) {

        return placeRepository.findPlacesByFilters(
                rangeLat.get(0), rangeLat.get(1),
                rangeLon.get(0), rangeLon.get(1)
        );
    }

    @Override
    public List<Place> getAllPathPlaces(List<UUID> ids) {
        return placeRepository.findAllByIdIn(ids);
    }
}
