package rnd.sueta.event_ms.dto;

import lombok.Builder;
import rnd.sueta.event_ms.enums.Category;
import rnd.sueta.event_ms.model.Event;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record PlaceDto(
        UUID id,

        String title,

        String type,

        BigDecimal latitude,

        BigDecimal longitude,

        String address,

        String image,

        Category category,

        List<Event> events
) {
}
