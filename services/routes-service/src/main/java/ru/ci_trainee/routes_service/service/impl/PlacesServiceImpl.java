package ru.ci_trainee.routes_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ci_trainee.routes_service.dto.response.PlacesRs;
import ru.ci_trainee.routes_service.dto.route.RouteRequest;
import ru.ci_trainee.routes_service.dto.searchEvents.SearchPlacesDto;
import ru.ci_trainee.routes_service.model.Util.Point;
import ru.ci_trainee.routes_service.service.PlacesService;
import ru.ci_trainee.routes_service.service.client.PlaceFeignClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlacesServiceImpl implements PlacesService {
    private final PlaceFeignClient placeFeignClient;

    public PlacesRs getOptimalPlaces(RouteRequest routeRequest, double paddingKm) {

        Point start = routeRequest.getStartPoint();
        Point end = routeRequest.getEndPoint();

        double[] boundingBox = createBoundingBox(start, end, paddingKm);

        List<Double> rangeLat = Arrays.asList(boundingBox[0], boundingBox[2]);
        List<Double> rangeLon = Arrays.asList(boundingBox[1], boundingBox[3]);

        SearchPlacesDto searchPlacesDto = new SearchPlacesDto(rangeLat, rangeLon, routeRequest.getCategories());

        return placeFeignClient.getPlaces(searchPlacesDto);
    }

    public double[] createBoundingBox(Point start, Point end, double paddingKm) {

        double minLat = Math.min(start.getLat(), end.getLat());
        double maxLat = Math.max(start.getLat(), end.getLat());
        double minLon = Math.min(start.getLon(), end.getLon());
        double maxLon = Math.max(start.getLon(), end.getLon());

        double latPadding = paddingKm / 111.0;
        double avgLat = (minLat + maxLat) / 2.0;
        double lonPadding = paddingKm / (111.0 * Math.cos(Math.toRadians(avgLat)));

        return new double[]{
                minLat - latPadding, // minLat
                minLon - lonPadding, // minLon
                maxLat + latPadding, // maxLat
                maxLon + lonPadding  // maxLon
        };
    }
}
