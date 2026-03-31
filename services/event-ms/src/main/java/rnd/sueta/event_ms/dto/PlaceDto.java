package rnd.sueta.event_ms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import rnd.sueta.event_ms.enums.PlaceType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlaceDto(

        UUID id,

        String title,

        String address,

        PlaceType type,

        BigDecimal averageRating,

        Integer totalVisits,

        UUID ownerId,

        BigDecimal latitude,

        BigDecimal longitude,

        List<String> mobileNumbers,

        String email,

        String websiteUrl,

        Map<String, String> social
) {
}
