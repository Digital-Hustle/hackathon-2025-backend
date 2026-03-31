package rnd.sueta.event_ms.service.entity;

import org.springframework.data.domain.Page;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.PlaceWithCoordinates;

public interface RecommendationService {

    Page<PlaceWithCoordinates> getAllPlaces(int page, int size);

    void refreshPlaces();

    Page<EventWithPlace> getAllEvents(int page, int size);

    void refreshEvents();
}
