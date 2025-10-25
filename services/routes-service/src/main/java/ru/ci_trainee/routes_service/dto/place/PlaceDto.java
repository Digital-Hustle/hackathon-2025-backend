package ru.ci_trainee.routes_service.dto.place;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ci_trainee.routes_service.dto.event.EventDto;
import ru.ci_trainee.routes_service.model.Util.Category;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto {
    private UUID id;

    private String title;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String address;

    private String image;

    private List<EventDto> events;
}
