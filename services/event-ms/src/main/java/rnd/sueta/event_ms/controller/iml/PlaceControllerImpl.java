package rnd.sueta.event_ms.controller.iml;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import rnd.sueta.event_ms.controller.PlaceController;
import rnd.sueta.event_ms.dto.request.PlacesIDsRq;
import rnd.sueta.event_ms.dto.request.SearchEventParamsRq;
import rnd.sueta.event_ms.dto.response.PlacesRs;
import rnd.sueta.event_ms.mapper.PlaceMapper;
import rnd.sueta.event_ms.model.Place;
import rnd.sueta.event_ms.service.PlaceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceControllerImpl implements PlaceController {
    private final PlaceService placeService;
    private final PlaceMapper placeMapper;

    @Override
    public PlacesRs getPlaces(SearchEventParamsRq params) {
        List<Place> places = placeService.getAllByCategories(
                params.rangeLat(),
                params.rangeLon()
        );

        return PlacesRs.builder()
                .places(
                        places.stream()
                                .map(placeMapper::convert)
                                .toList())
                .build();
    }

    @Override
    public PlacesRs getPlacesByIDs(PlacesIDsRq params) {
        var places = placeService.getAllPathPlaces(params.ids());
        return PlacesRs.builder()
                .places(
                        places.stream()
                                .map(placeMapper::convert)
                                .toList()
                )
                .build();
    }
}
