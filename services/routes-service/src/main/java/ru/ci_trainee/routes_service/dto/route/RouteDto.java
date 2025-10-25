package ru.ci_trainee.routes_service.dto.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ci_trainee.routes_service.dto.place.PlaceDto;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto {
    private UUID id;
    private List<PlaceDto> places;
}
