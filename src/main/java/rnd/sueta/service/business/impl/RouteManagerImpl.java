package rnd.sueta.service.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import rnd.sueta.config.ThreadLocalMap;
import rnd.sueta.constants.ContextKeys;
import rnd.sueta.model.EventWithPlace;
import rnd.sueta.model.PlaceWithCoordinates;
import rnd.sueta.model.RouteGenerationParams;
import rnd.sueta.model.RouteWithEvents;
import rnd.sueta.model.RouteWithPlaces;
import rnd.sueta.model.entity.Point;
import rnd.sueta.model.entity.Route;
import rnd.sueta.service.business.RouteManager;
import rnd.sueta.service.entity.EventService;
import rnd.sueta.service.entity.PlaceService;
import rnd.sueta.service.entity.RouteService;
import rnd.sueta.util.DistanceCalculator;
import rnd.sueta.validator.PointValidator;
import rnd.sueta.validator.ProfileValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteManagerImpl implements RouteManager {

    private final RouteService routeService;
    private final PlaceService placeService;
    private final EventService eventService;
    private final PointValidator pointValidator;

    @Override
    public RouteWithPlaces getRoute(UUID routeId) {
        Route route = routeService.getById(routeId);
        List<PlaceWithCoordinates> places = placeService.getByRouteId(routeId);

        return RouteWithPlaces.builder()
                .id(route.getId())
                .profileId(route.getProfileId())
                .places(places)
                .build();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public RouteWithEvents generateRoute(RouteGenerationParams routeGenerationParams) {
        String profileId = ThreadLocalMap.get(ContextKeys.PROFILE_ID);
        ProfileValidator.checkProfileIdIsPresent(profileId);
        ProfileValidator.checkProfileIdIsUuid(profileId);

        Point startRoutePoint = routeGenerationParams.startPoint();
        Point endRoutePoint = routeGenerationParams.endPoint();

        pointValidator.checkRoutePointsWithinCityArea(startRoutePoint, endRoutePoint);

        List<EventWithPlace> events = eventService.getAllInRangeByCategories(
                startRoutePoint, endRoutePoint,
                routeGenerationParams.categories()
        );

        Map<EventWithPlace, BigDecimal> placeDistanceMap = DistanceCalculator.getDistanceMap(startRoutePoint, events);

        events.sort((firstPlace, secondPlace) -> {
            BigDecimal distanceForFirstPlace = placeDistanceMap.get(firstPlace);
            BigDecimal distanceForSecondPlace = placeDistanceMap.get(secondPlace);

            return distanceForFirstPlace.compareTo(distanceForSecondPlace);
        });

        Route savedRoute = routeService.saveRouteWithPlaces(events, UUID.fromString(profileId));
        return RouteWithEvents.builder()
                .id(savedRoute.getId())
                .events(events)
                .build();
    }
}
