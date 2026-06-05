package rnd.sueta.service.business;

import rnd.sueta.model.RouteGenerationParams;
import rnd.sueta.model.RouteWithEvents;
import rnd.sueta.model.RouteWithPlaces;

import java.util.UUID;

public interface RouteManager {
    RouteWithPlaces getRoute(UUID routeId);

    RouteWithEvents generateRoute(RouteGenerationParams routeGenerationParams);
}
