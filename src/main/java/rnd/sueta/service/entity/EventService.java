package rnd.sueta.service.entity;

import org.springframework.data.domain.Page;
import rnd.sueta.enums.EventType;
import rnd.sueta.model.EventFilterParams;
import rnd.sueta.model.EventScoreParts;
import rnd.sueta.model.EventWithPlace;
import rnd.sueta.model.ItemWithScore;
import rnd.sueta.model.entity.Event;
import rnd.sueta.model.entity.Point;

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
