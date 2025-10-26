package ru.ci_trainee.routes_service.dto.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ci_trainee.routes_service.model.Util.Category;
import ru.ci_trainee.routes_service.model.Util.Point;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteRequest {
    private Point startPoint;

    private Point endPoint;

    private String budget;

    private String style;

    private List<Category> categories;

    private String routeLength;

    private UUID profileId;
}
