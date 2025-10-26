package ru.ci_trainee.routes_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.ci_trainee.routes_service.dto.response.SuccessRs;
import ru.ci_trainee.routes_service.dto.route.RouteDto;
import ru.ci_trainee.routes_service.dto.route.RouteRequest;
import ru.ci_trainee.routes_service.service.FindRouteService;
import ru.ci_trainee.routes_service.service.RouteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routes")
public class RoutController {

    private final FindRouteService findRouteService;
    private final RouteService routeService;

    @PostMapping("/generate")
    public Mono<RouteDto> generateRoute(@RequestBody RouteRequest routeRequest) {
        if (routeRequest.getRouteLength().equals("Оптимальный")) {
            return findRouteService.findOptimalRoute(routeRequest);
        } else {
            return findRouteService.findLargeRoute(routeRequest);
        }
    }

    @GetMapping("/{id}")
    public SuccessRs<RouteDto> getRouteById(@PathVariable UUID id) {
        RouteDto routeDto = routeService.getRouteById(id);
        return new SuccessRs<>("Route found", routeDto);
    }

    @GetMapping("/history")
    public SuccessRs<List<RouteDto>> getRouteHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam UUID profileId
            ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<RouteDto> routePage = routeService.getRouteHistory(pageable, profileId);

        return new SuccessRs<>("Route history retrieved", routePage.getContent());
    }
}