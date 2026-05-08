package rnd.sueta.event_ms.service.entity;

import org.springframework.data.domain.Page;
import rnd.sueta.event_ms.enums.EventType;
import rnd.sueta.event_ms.model.EventFilterParams;
import rnd.sueta.event_ms.model.EventScoreParts;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.ItemWithScore;
import rnd.sueta.event_ms.model.entity.Event;
import rnd.sueta.event_ms.model.entity.Point;

import java.util.List;
import java.util.UUID;

public interface EventService {

    List<ItemWithScore<EventWithPlace>> getTopWithScores();

    Page<EventWithPlace> getAllByFilter(EventFilterParams eventFilterParams);

    List<EventWithPlace> getAllInRangeByCategories(Point startPoint, Point endPoint, List<EventType> categories);

    EventWithPlace getEventWithPlaceById(UUID id);

    EventScoreParts getScorePartsById(UUID id);

    boolean exists(UUID id);

    Event create(Event event);

    EventWithPlace update(EventWithPlace event);

    void refreshTop();

    void incrementRating(UUID id, Integer newRate);

    void updateRating(UUID id, Integer oldRate, Integer newRate);

    void decrementRating(UUID id, Integer newRate);

    void delete(UUID id);
}
