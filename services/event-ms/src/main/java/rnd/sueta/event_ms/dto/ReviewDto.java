package rnd.sueta.event_ms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReviewDto(

        UUID id,

        UUID profileId,

        Integer rate,

        String comment,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
