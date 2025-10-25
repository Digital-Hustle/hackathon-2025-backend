package ru.ci_trainee.routes_service.service;

import ru.ci_trainee.routes_service.dto.route.RouteDto;
import ru.ci_trainee.routes_service.dto.route.RouteRequest;

import java.util.UUID;

public interface RouteService {
    UUID saveRoute(RouteDto routeDto, RouteRequest routeRequest);
}
