package ru.ci_trainee.routes_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.ci_trainee.routes_service.dto.PlacesRs;
import ru.ci_trainee.routes_service.dto.place.PlaceDto;
import ru.ci_trainee.routes_service.dto.route.RouteRequest;
import ru.ci_trainee.routes_service.service.FindRouteService;
import ru.ci_trainee.routes_service.service.PlacesService;
import ru.ci_trainee.routes_service.service.YandexGptService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindRouteServiceImpl implements FindRouteService {

    private final PlacesService placesService;
    private final YandexGptService yandexGptService;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public Mono<List<PlaceDto>> findOptimalRoute(RouteRequest routeRequest) {
        PlacesRs optimalPlacesRs = placesService.getOptimalPlaces(routeRequest, 8);
        String prompt = buildPrompt(routeRequest, optimalPlacesRs);

        return yandexGptService.generateResponseAsync(prompt, 0.3, 2000)
                .map(response -> filterPlacesByIds(response, optimalPlacesRs, routeRequest));
    }

    public Mono<List<PlaceDto>> findLargeRoute(RouteRequest routeRequest) {
        PlacesRs optimalPlacesRs = placesService.getOptimalPlaces(routeRequest, 16);
        String prompt = buildPrompt(routeRequest, optimalPlacesRs);

        return yandexGptService.generateResponseAsync(prompt, 0.3, 2000)
                .map(response -> filterPlacesByIds(response, optimalPlacesRs, routeRequest));
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

    private List<PlaceDto> filterPlacesByIds(String aiResponse, PlacesRs placesRs, RouteRequest routeRequest) {
        try {
            List<String> selectedIds = objectMapper.readValue(
                    aiResponse,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, String.class)
            );

            System.out.println("AI selected " + selectedIds.size() + " IDs: " + selectedIds);

            List<PlaceDto> selectedPlaces = selectedIds.stream()
                    .map(id -> placesRs.places().stream()
                            .filter(place -> id.equals(place.getId().toString()))
                            .findFirst()
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            List<PlaceDto> sortedPlaces = sortPlacesByRoute(selectedPlaces, routeRequest);

            return sortedPlaces;

        } catch (Exception e) {
            return placesRs.places();
        }
    }

    private List<PlaceDto> sortPlacesByRoute(List<PlaceDto> places, RouteRequest routeRequest) {
        if (places.isEmpty()) return places;

        PlaceDto startNearest = findNearestPlace(places,
                routeRequest.getStartPoint().getLat(),
                routeRequest.getStartPoint().getLon());

        PlaceDto endNearest = findNearestPlace(places,
                routeRequest.getEndPoint().getLat(),
                routeRequest.getEndPoint().getLon());

        List<PlaceDto> remaining = new ArrayList<>(places);
        List<PlaceDto> sorted = new ArrayList<>();

        if (!startNearest.getId().equals(endNearest.getId())) {
            remaining.remove(startNearest);
            remaining.remove(endNearest);
            sorted.add(startNearest);

            double currentLat = startNearest.getLatitude().doubleValue();
            double currentLon = startNearest.getLongitude().doubleValue();

            while (!remaining.isEmpty()) {
                PlaceDto nearest = findNearestPlace(remaining, currentLat, currentLon);
                sorted.add(nearest);
                remaining.remove(nearest);

                currentLat = nearest.getLatitude().doubleValue();
                currentLon = nearest.getLongitude().doubleValue();
            }

            sorted.add(endNearest);
        } else {
            remaining.remove(startNearest);
            sorted.add(startNearest);

            double currentLat = startNearest.getLatitude().doubleValue();
            double currentLon = startNearest.getLongitude().doubleValue();

            while (!remaining.isEmpty()) {
                PlaceDto nearest = findNearestPlace(remaining, currentLat, currentLon);
                sorted.add(nearest);
                remaining.remove(nearest);

                currentLat = nearest.getLatitude().doubleValue();
                currentLon = nearest.getLongitude().doubleValue();
            }
        }

        for (int i = 0; i < sorted.size(); i++) {
            PlaceDto place = sorted.get(i);
            System.out.println((i + 1) + ". " + place.getTitle());
        }

        return sorted;
    }

    private PlaceDto findNearestPlace(List<PlaceDto> places, double currentLat, double currentLon) {
        PlaceDto nearest = places.getFirst();
        double minDistance = calculateDistance(currentLat, currentLon,
                nearest.getLatitude().doubleValue(), nearest.getLongitude().doubleValue());

        for (int i = 1; i < places.size(); i++) {
            PlaceDto place = places.get(i);
            double distance = calculateDistance(currentLat, currentLon,
                    place.getLatitude().doubleValue(), place.getLongitude().doubleValue());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = place;
            }
        }

        return nearest;
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