package ru.ci_trainee.routes_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ci_trainee.routes_service.dto.response.PlacesRs;
import ru.ci_trainee.routes_service.dto.route.RouteDto;
import ru.ci_trainee.routes_service.dto.route.RouteRequest;
import ru.ci_trainee.routes_service.dto.searchPlaces.SearchPlacesByIds;
import ru.ci_trainee.routes_service.model.Route;
import ru.ci_trainee.routes_service.model.RouteId;
import ru.ci_trainee.routes_service.repository.RouteRepository;
import ru.ci_trainee.routes_service.service.RouteService;
import ru.ci_trainee.routes_service.service.client.PlaceFeignClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final PlaceFeignClient placeFeignClient;

    @Transactional
    public UUID saveRoute(RouteDto routeDto, RouteRequest routeRequest) {
        UUID routeId = routeDto.getId();
        UUID profileId = routeRequest.getProfileId();

        List<Route> routes = new ArrayList<>();
        for (int i = 0; i < routeDto.getPlaces().size(); i++) {
            RouteId routeKey = new RouteId();
            routeKey.setId(routeId);
            routeKey.setPlaceId(routeDto.getPlaces().get(i).getId());

            Route route = Route.builder()
                    .routeId(routeKey)
                    .profileId(profileId)
                    .build();
            routes.add(route);
        }

        routeRepository.saveAll(routes);
        return routeId;
    }

    @Transactional
    public RouteDto getRouteById(UUID routeId) {
        List<Route> routeEntities = routeRepository.findByRouteId_Id(routeId);

        List<UUID> placeIds = routeEntities.stream()
                .map(route -> route.getRouteId().getPlaceId())
                .toList();

        PlacesRs places = placeFeignClient.getPlacesByIds(new SearchPlacesByIds(placeIds));

        return new RouteDto(routeId, places.places());
    }

    @Transactional(readOnly = true)
    public Page<RouteDto> getRouteHistory(Pageable pageable, UUID profileId) {
        Page<UUID> routeIdsPage = routeRepository.findDistinctRouteIdsByProfileId(
                profileId,
                pageable
        );

        List<RouteDto> routeDtos = routeIdsPage.getContent().stream()
                .map(this::getRouteById)
                .collect(Collectors.toList());

        return new PageImpl<>(
                routeDtos,
                pageable,
                routeIdsPage.getTotalElements()
        );
    }
}
