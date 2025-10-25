package ru.ci_trainee.routes_service.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private UUID id;

    private String title;

    private LocalDateTime date;

    private Duration duration;

    private BigDecimal price;

    private Integer ageRestriction;
}
