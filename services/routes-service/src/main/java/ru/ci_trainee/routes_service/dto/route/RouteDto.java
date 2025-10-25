package ru.ci_trainee.routes_service.dto.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto {

    private UUID id;
    private UUID place_id;
    private UUID profile_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
