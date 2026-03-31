package rnd.sueta.event_ms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import rnd.sueta.event_ms.dto.PlaceDto;

import java.util.List;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RouteWithPlacesRs(

        UUID id,

        UUID profileId,

        List<PlaceDto> places
) {
}
