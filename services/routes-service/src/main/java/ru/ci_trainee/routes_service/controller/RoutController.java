package ru.ci_trainee.routes_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.ci_trainee.routes_service.dto.place.PlaceDto;
import ru.ci_trainee.routes_service.dto.route.RouteDto;
import ru.ci_trainee.routes_service.dto.route.RouteRequest;
import ru.ci_trainee.routes_service.service.FindRouteService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routes")
public class RoutController {

    private final FindRouteService findRouteService;

    @PostMapping("/generate")
    public Mono<RouteDto> generateRoute(@RequestBody RouteRequest routeRequest) {
        if (routeRequest.getRouteLength().equals("Оптимальный")) {
            return findRouteService.findOptimalRoute(routeRequest);
        } else {
            return findRouteService.findLargeRoute(routeRequest);
        }
    }
}