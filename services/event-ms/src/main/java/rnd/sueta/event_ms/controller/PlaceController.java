package rnd.sueta.event_ms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.sueta.event_ms.dto.request.PlacesIDsRq;
import rnd.sueta.event_ms.dto.request.SearchEventParamsRq;
import rnd.sueta.event_ms.dto.response.PlacesRs;

@RequestMapping("/api/v1/places")
public interface PlaceController {
    @PostMapping // TODO подумать, как переделать на GET
    PlacesRs getPlaces(@RequestBody SearchEventParamsRq params);

    @PostMapping("/route")
    PlacesRs getPlacesByIDs(@RequestBody PlacesIDsRq params);
}
