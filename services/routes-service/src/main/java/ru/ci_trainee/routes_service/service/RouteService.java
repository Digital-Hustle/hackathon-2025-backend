package ru.ci_trainee.routes_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.ci_trainee.routes_service.dto.route.RouteDto;
import ru.ci_trainee.routes_service.dto.route.RouteRequest;

import java.util.UUID;

public interface RouteService {
    UUID saveRoute(RouteDto routeDto, RouteRequest routeRequest);
    RouteDto getRouteById(UUID routeId);
    Page<RouteDto> getRouteHistory(Pageable pageable, UUID profileId);
}
