package rnd.sueta.event_ms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.data.domain.Page;
import rnd.sueta.event_ms.dto.ReviewDto;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetReviewsRs(

        Page<ReviewDto> reviews
) {
}
