package rnd.sueta.event_ms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import rnd.sueta.event_ms.controller.RouteController;
import rnd.sueta.event_ms.dto.request.CreateRouteRq;
import rnd.sueta.event_ms.dto.response.RouteWithDetailsRs;
import rnd.sueta.event_ms.dto.response.RouteWithPlacesRs;
import rnd.sueta.event_ms.mapper.RouteMapper;
import rnd.sueta.event_ms.model.RouteGenerationParams;
import rnd.sueta.event_ms.model.RouteWithEvents;
import rnd.sueta.event_ms.model.RouteWithPlaces;
import rnd.sueta.event_ms.service.business.RouteManager;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RouteControllerImpl implements RouteController {

    private final RouteManager routeManager;
    private final RouteMapper routeMapper;

    @Override
    public RouteWithPlacesRs getById(UUID id) {
        RouteWithPlaces route = routeManager.getRoute(id);

        return routeMapper.convert(route);
    }

    @Override
    public RouteWithDetailsRs generateRoute(CreateRouteRq createRouteRq) {
        RouteGenerationParams routeGenerationParams = routeMapper.convert(createRouteRq);
        RouteWithEvents route = routeManager.generateRoute(routeGenerationParams);

        return routeMapper.convert(route);
    }
}
