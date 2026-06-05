package rnd.sueta.service.entity;

import org.springframework.data.domain.Page;
import rnd.sueta.model.ItemWithScore;
import rnd.sueta.model.PlaceScoreParts;
import rnd.sueta.model.PlaceWithCoordinates;

import java.util.List;
import java.util.UUID;

public interface PlaceService {

    Page<PlaceWithCoordinates> getAll(int page, int size);

    List<ItemWithScore<PlaceWithCoordinates>> getTopWithScores();

    List<PlaceWithCoordinates> getByRouteId(UUID routeId);

    PlaceWithCoordinates getById(UUID id);

    PlaceScoreParts getScorePartsById(UUID id);

    boolean exists(UUID id);

    PlaceWithCoordinates create(PlaceWithCoordinates place);

    PlaceWithCoordinates update(PlaceWithCoordinates place);

    void refreshTop();

    void incrementRating(UUID id, Integer newRate);

    void updateRating(UUID id, Integer oldRate, Integer newRate);

    void decrementRating(UUID id, Integer newRate);

    void delete(UUID id);
}
