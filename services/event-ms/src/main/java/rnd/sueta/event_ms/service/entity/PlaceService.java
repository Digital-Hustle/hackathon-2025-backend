package rnd.sueta.event_ms.service.entity;

import org.springframework.data.domain.Page;
import rnd.sueta.event_ms.model.PlaceWithCoordinates;

import java.util.List;
import java.util.UUID;

public interface PlaceService {

    Page<PlaceWithCoordinates> getAll(int page, int size);

    List<PlaceWithCoordinates> getByRouteId(UUID routeId);

    PlaceWithCoordinates getById(UUID id);

    PlaceWithCoordinates create(PlaceWithCoordinates place);

    PlaceWithCoordinates update(UUID id, PlaceWithCoordinates place);

    void incrementRating(UUID id, Integer newRate);

    void updateRating(UUID id, Integer oldRate, Integer newRate);

    void decrementRating(UUID id, Integer newRate);

    void delete(UUID id);

    boolean exists(UUID id);
}
