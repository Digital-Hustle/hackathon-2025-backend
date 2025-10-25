package ru.ci_trainee.routes_service.service;

import ru.ci_trainee.routes_service.dto.PlacesRs;
import ru.ci_trainee.routes_service.dto.route.RouteRequest;

public interface PlacesService {
    PlacesRs getOptimalPlaces(RouteRequest routeRequest, double paddingKm);
}
