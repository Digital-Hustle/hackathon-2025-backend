package rnd.sueta.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rnd.sueta.helper.RatingHelper;
import rnd.sueta.model.ItemWithScore;
import rnd.sueta.model.PlaceScoreParts;
import rnd.sueta.model.PlaceWithCoordinates;
import rnd.sueta.model.entity.Point;
import rnd.sueta.repository.PlaceRepository;
import rnd.sueta.service.entity.PlaceService;
import rnd.sueta.validator.PointValidator;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PointValidator pointValidator;
    private final RatingHelper ratingHelper;

    @Override
    public Page<PlaceWithCoordinates> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return placeRepository.findAll(pageable);
    }

    @Override
    public List<ItemWithScore<PlaceWithCoordinates>> getTopWithScores() {
        return placeRepository.findTopWithScores();
    }

    @Override
    public List<PlaceWithCoordinates> getByRouteId(UUID routeId) {
        return placeRepository.findAllByRouteId(routeId);
    }

    @Override
    public PlaceWithCoordinates getById(UUID id) {
        PlaceWithCoordinates place = placeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        double averageRating = ratingHelper.countAverage(place.totalRating(), place.reviewsAmount());

        return place.toBuilder()
                .averageRating(averageRating)
                .build();
    }

    @Override
    public PlaceScoreParts getScorePartsById(UUID id) {
        return placeRepository.findScoreParts(id);
    }

    @Override
    public boolean exists(UUID id) {
        return placeRepository.existsById(id);
    }

    @Transactional
    @Override
    public PlaceWithCoordinates create(PlaceWithCoordinates place) {
        Point placePoint = Point.builder()
                .longitude(place.longitude())
                .latitude(place.latitude())
                .build();

        pointValidator.checkPointWithinCityArea(placePoint);

        return placeRepository.save(
                place.toBuilder()
                        .id(UUID.randomUUID())
                        .build()
        );
    }

    @Transactional
    @Override
    public PlaceWithCoordinates update(PlaceWithCoordinates place) {
        PlaceWithCoordinates dbPlace = getById(place.id());

        dbPlace = dbPlace.toBuilder()
                .title(place.title())
                .type(place.type())
                .ownerId(place.ownerId())
                .recommended(place.recommended())
                .contacts(place.contacts())
                .build();

        return placeRepository.save(dbPlace);
    }

    @Override
    public void refreshTop() {
        placeRepository.refreshTopPlaces();
    }

    @Override
    public void incrementRating(UUID id, Integer newRate) {
        placeRepository.incrementRating(id, newRate);
    }

    @Override
    public void updateRating(UUID id, Integer oldRate, Integer newRate) {
        placeRepository.updateRating(id, oldRate, newRate);
    }

    @Override
    public void decrementRating(UUID id, Integer oldRate) {
        placeRepository.decrementRating(id, oldRate);
    }

    @Override
    public void delete(UUID id) {
        placeRepository.deleteById(id);
    }
}
