package ru.ci_trainee.routes_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import ru.ci_trainee.routes_service.dto.place.PlaceDto;

import java.util.List;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlacesRs(
        List<PlaceDto> places
) {
}
