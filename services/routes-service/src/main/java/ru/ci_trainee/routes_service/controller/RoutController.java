package ru.ci_trainee.routes_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.ci_trainee.routes_service.dto.place.PlaceDto;
import ru.ci_trainee.routes_service.dto.route.RouteRequest;
import ru.ci_trainee.routes_service.service.FindRouteService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routes")
public class RoutController {

    private final FindRouteService findRouteService;

    @PostMapping("/test")
    public Mono<List<PlaceDto>> createOptimalRoute(@RequestBody RouteRequest routeRequest) {
        return findRouteService.findOptimalRoute(routeRequest);
    }

    @PostMapping("/large")
    public Mono<List<PlaceDto>> createLargeRoute(@RequestBody RouteRequest routeRequest) {
        return findRouteService.findLargeRoute(routeRequest);
    }

    @GetMapping("/test")
    public String createLargeRoute() {
        return "Sau";
    }
}