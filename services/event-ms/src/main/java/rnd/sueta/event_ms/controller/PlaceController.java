package rnd.sueta.event_ms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.sueta.event_ms.dto.request.SearchEventParamsRq;
import rnd.sueta.event_ms.dto.response.PlacesRs;

@RequestMapping("/api/v1/places")
public interface PlaceController {
    @GetMapping
    PlacesRs getPlaces(@RequestBody SearchEventParamsRq params);
}
