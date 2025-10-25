package ru.ci_trainee.routes_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.ci_trainee.routes_service.dto.place.PlaceDto;
import ru.ci_trainee.routes_service.dto.response.PlacesRs;
import ru.ci_trainee.routes_service.dto.route.RouteDto;
import ru.ci_trainee.routes_service.dto.route.RouteRequest;
import ru.ci_trainee.routes_service.service.FindRouteService;
import ru.ci_trainee.routes_service.service.PlacesService;
import ru.ci_trainee.routes_service.service.RouteService;
import ru.ci_trainee.routes_service.service.YandexGptService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindRouteServiceImpl implements FindRouteService {

    private final PlacesService placesService;
    private final YandexGptService yandexGptService;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private final RouteService routeService;

    public Mono<RouteDto> findOptimalRoute(RouteRequest routeRequest) {
        PlacesRs optimalPlacesRs = placesService.getOptimalPlaces(routeRequest, 8);
        String prompt = buildPrompt(routeRequest, optimalPlacesRs);

        return yandexGptService.generateResponseAsync(prompt, 0.3, 2000)
                .map(response -> {
                    RouteDto routeDto = createRoute(response, optimalPlacesRs, routeRequest);

                    routeService.saveRoute(routeDto, routeRequest);
                    return routeDto;
                });
    }

    public Mono<RouteDto> findLargeRoute(RouteRequest routeRequest) {
        PlacesRs optimalPlacesRs = placesService.getOptimalPlaces(routeRequest, 16);
        String prompt = buildPrompt(routeRequest, optimalPlacesRs);

        return yandexGptService.generateResponseAsync(prompt, 0.3, 2000)
                .map(response -> {
                    RouteDto routeDto = createRoute(response, optimalPlacesRs, routeRequest);

                    routeService.saveRoute(routeDto, routeRequest);
                    return routeDto;
                });
    }

    private RouteDto createRoute(String aiResponse, PlacesRs placesRs, RouteRequest routeRequest) {
        try {
            List<String> selectedIds = objectMapper.readValue(
                    aiResponse,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, String.class)
            );

            List<PlaceDto> selectedPlaces = selectedIds.stream()
                    .map(id -> placesRs.places().stream()
                            .filter(place -> id.equals(place.getId().toString()))
                            .findFirst()
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            List<PlaceDto> sortedPlaces = sortPlacesByRoute(selectedPlaces, routeRequest);

            UUID routeId = UUID.randomUUID();
            return new RouteDto(routeId, sortedPlaces);

        } catch (Exception e) {
            List<PlaceDto> sortedPlaces = sortPlacesByRoute(placesRs.places(), routeRequest);
            UUID routeId = UUID.randomUUID();
            return new RouteDto(routeId, sortedPlaces);
        }
    }

    private String buildPrompt(RouteRequest routeRequest, PlacesRs placesRs) {
        try {
            Resource resource = resourceLoader.getResource("classpath:prompt.txt");
            String promptTemplate;
            try (InputStream inputStream = resource.getInputStream()) {
                promptTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }

            String interestsJson = objectMapper.writeValueAsString(
                    routeRequest.getCategories().stream()
                            .map(category -> category.name().toLowerCase())
                            .toList()
            );

            String placesJson = objectMapper.writeValueAsString(placesRs.places());

            return promptTemplate
                    .replace("${USER_INTERESTS}", interestsJson)
                    .replace("${USER_BUDGET}", routeRequest.getBudget())
                    .replace("${TRIP_TYPE}", routeRequest.getStyle())
                    .replace("${AVAILABLE_POIS}", placesJson);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read prompt template", e);
        }
    }

    private List<PlaceDto> sortPlacesByRoute(List<PlaceDto> places, RouteRequest routeRequest) {
        if (places.isEmpty()) return places;

        return places.stream()
                .sorted((p1, p2) -> {
                    double dist1 = calculateDistance(
                            routeRequest.getStartPoint().getLat(),
                            routeRequest.getStartPoint().getLon(),
                            p1.getLatitude().doubleValue(),
                            p1.getLongitude().doubleValue()
                    );
                    double dist2 = calculateDistance(
                            routeRequest.getStartPoint().getLat(),
                            routeRequest.getStartPoint().getLon(),
                            p2.getLatitude().doubleValue(),
                            p2.getLongitude().doubleValue()
                    );
                    return Double.compare(dist1, dist2);
                })
                .collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}