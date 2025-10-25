package rnd.sueta.event_ms.dto;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public record EventDto(
        UUID id,

        String title,

        LocalDateTime date,

        Duration duration,

        BigDecimal price,

        Integer ageRestriction
) {
}
