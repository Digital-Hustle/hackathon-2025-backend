package rnd.sueta.event_ms.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rnd.sueta.event_ms.constants.EventConstants;
import rnd.sueta.event_ms.enums.EventType;
import rnd.sueta.event_ms.model.DateTimeRange;
import rnd.sueta.event_ms.model.EventFilterParams;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.entity.Event;
import rnd.sueta.event_ms.model.entity.Point;
import rnd.sueta.event_ms.repository.EventRepository;
import rnd.sueta.event_ms.service.entity.EventService;
import rnd.sueta.event_ms.util.DateTimeRangeFactory;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public Page<EventWithPlace> getAllByFilter(EventFilterParams eventFilterParams) {
        DateTimeRange range = DateTimeRangeFactory.getRange(
                eventFilterParams.date(),
                EventConstants.ONE_DAY_AFTER_START
        );

        Pageable pageable = PageRequest.of(eventFilterParams.page(), eventFilterParams.size());

        return eventFilterParams.categories() != null
                ? getAllByDateAndCategories(eventFilterParams.categories(), range, pageable)
                : getAllByDate(range, pageable);
    }

    @Override
    public List<EventWithPlace> getAllInRangeByCategories(Point startPoint, Point endPoint, List<EventType> categories) {
        return eventRepository.findAllInRangeByCategories(startPoint, endPoint, categories);
    }

    @Override
    public EventWithPlace getEventWithPlaceById(UUID id) {
        return eventRepository.findEventWithPlaceByEventId(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Event create(Event event) {
        return eventRepository.save(
                event.toBuilder()
                        .id(UUID.randomUUID())
                        .build()
        );
    }

    @Transactional
    @Override
    public Event update(UUID id, Event event) {
        return eventRepository.save(
                event.toBuilder()
                        .id(id)
                        .build()
        );
    }

    @Override
    public void incrementRating(UUID id, Integer newRate) {
        eventRepository.incrementRating(id, newRate);
    }

    @Override
    public void updateRating(UUID id, Integer oldRate, Integer newRate) {
        eventRepository.updateRating(id, oldRate, newRate);
    }

    @Override
    public void decrementRating(UUID id, Integer oldRate) {
        eventRepository.decrementRating(id, oldRate);
    }

    @Override
    public void delete(UUID id) {
        eventRepository.deleteById(id);
    }

    @Override
    public boolean exists(UUID id) {
        return eventRepository.existsById(id);
    }

    private Page<EventWithPlace> getAllByDate(DateTimeRange range, Pageable pageable) {
        return eventRepository.findEventsWithPlaceByDate(range, pageable);
    }

    private Page<EventWithPlace> getAllByDateAndCategories(
            List<EventType> categories,
            DateTimeRange range,
            Pageable pageable) {
        return eventRepository.findEventsWithPlaceByDateAndCategories(categories, range, pageable);
    }
}
