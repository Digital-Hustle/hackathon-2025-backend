package ru.ci_trainee.routes_service.service;

import reactor.core.publisher.Mono;
import ru.ci_trainee.routes_service.dto.place.PlaceDto;
import ru.ci_trainee.routes_service.dto.route.RouteRequest;

import java.util.List;

public interface FindRouteService {
    public Mono<List<PlaceDto>> findOptimalRoute(RouteRequest routeRequest);

    public Mono<List<PlaceDto>> findLargeRoute(RouteRequest routeRequest);
}
