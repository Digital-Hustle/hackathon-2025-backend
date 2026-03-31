package rnd.sueta.event_ms.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rnd.sueta.event_ms.helper.RouteHelper;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.RouteEvents;
import rnd.sueta.event_ms.model.RoutePlaces;
import rnd.sueta.event_ms.model.entity.Route;
import rnd.sueta.event_ms.repository.RouteRepository;
import rnd.sueta.event_ms.service.entity.RouteService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final RouteHelper routeHelper;

    @Override
    public Route getById(UUID id) {
        return routeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @Override
    public Route saveRouteWithPlaces(List<EventWithPlace> places, UUID profileId) {
        final UUID routeId = UUID.randomUUID();
        Route route = Route.builder()
                .id(routeId)
                .profileId(profileId)
                .createdAt(OffsetDateTime.now())
                .build();

        List<EventWithPlace> uniquePlaces = routeHelper.getUniqueRoutePlaces(places);
        List<RoutePlaces> routePlaces = routeHelper.mapRoutePlaces(routeId, uniquePlaces);
        List<RouteEvents> routeEvents = routeHelper.mapRouteEvents(routeId, places);

        return save(route, routePlaces, routeEvents);
    }

    @Transactional
    private Route save(Route route, List<RoutePlaces> routePlaces, List<RouteEvents> routeEvents) {
        Route savedRoute = routeRepository.save(route);
        routeRepository.saveRoutePlaces(routePlaces);
        routeRepository.saveRouteEvents(routeEvents);

        return savedRoute;
    }
}
