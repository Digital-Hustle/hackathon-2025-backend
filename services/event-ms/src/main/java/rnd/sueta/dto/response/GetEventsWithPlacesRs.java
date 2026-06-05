package rnd.sueta.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.data.domain.Page;
import rnd.sueta.dto.EventWithPlaceDto;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetEventsWithPlacesRs(
        Page<EventWithPlaceDto> places
) {
}
