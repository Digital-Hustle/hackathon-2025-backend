package ru.ci_trainee.routes_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ci_trainee.routes_service.dto.route.RouteDto;
import ru.ci_trainee.routes_service.dto.route.RouteRequest;
import ru.ci_trainee.routes_service.model.Route;
import ru.ci_trainee.routes_service.repository.RouteRepository;
import ru.ci_trainee.routes_service.service.RouteService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @Transactional
    public UUID saveRoute(RouteDto routeDto, RouteRequest routeRequest) {
        UUID routeId = routeDto.getId();

        for (int i = 0; i < routeDto.getPlaces().size(); i++) {
            Route route = new Route();
            route.setId(routeId);
            route.setPlaceId(routeDto.getPlaces().get(i).getId());
            // TODO впихнуть айди профиля из jwt
//            route.setProfileId();

            routeRepository.save(route);
        }

        return routeId;
    }

}
