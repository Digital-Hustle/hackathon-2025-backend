package rnd.sueta.service.entity;

import rnd.sueta.model.EventWithPlace;
import rnd.sueta.model.entity.Route;

import java.util.List;
import java.util.UUID;

public interface RouteService {
    Route getById(UUID routeId);

    Route saveRouteWithPlaces(List<EventWithPlace> places, UUID profileId);
}
