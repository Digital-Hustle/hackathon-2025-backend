package ru.ci_trainee.routes_service.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ci_trainee.routes_service.dto.response.PlacesRs;
import ru.ci_trainee.routes_service.dto.searchEvents.SearchPlacesDto;

@FeignClient(
        name = "placeFeignClient",
        url = "http://192.168.0.109:4002"
)
public interface PlaceFeignClient {

    @PostMapping("/api/v1/places")
    PlacesRs getPlaces(@RequestBody SearchPlacesDto searchEventsDto);
}
