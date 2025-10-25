package rnd.sueta.event_ms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import rnd.sueta.event_ms.dto.PlaceDto;

import java.util.List;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlacesRs(
        List<PlaceDto> places
) {
}
