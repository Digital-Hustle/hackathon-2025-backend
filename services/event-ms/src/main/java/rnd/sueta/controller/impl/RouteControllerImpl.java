package rnd.sueta.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import rnd.sueta.controller.RouteController;
import rnd.sueta.dto.request.CreateRouteRq;
import rnd.sueta.dto.response.RouteWithDetailsRs;
import rnd.sueta.dto.response.RouteWithPlacesRs;
import rnd.sueta.mapper.RouteMapper;
import rnd.sueta.model.RouteGenerationParams;
import rnd.sueta.model.RouteWithEvents;
import rnd.sueta.model.RouteWithPlaces;
import rnd.sueta.service.business.RouteManager;

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
