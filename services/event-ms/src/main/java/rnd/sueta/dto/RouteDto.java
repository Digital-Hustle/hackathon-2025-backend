package rnd.sueta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RouteDto(
        UUID id,

        UUID profileId,

        List<PlaceDto> places
) {
}
