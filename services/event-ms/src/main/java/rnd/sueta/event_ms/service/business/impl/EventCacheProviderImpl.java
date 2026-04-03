package rnd.sueta.event_ms.service.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rnd.sueta.event_ms.gateway.RedisGateway;
import rnd.sueta.event_ms.model.EventScoreParts;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.ItemWithScore;
import rnd.sueta.event_ms.service.business.CacheProvider;
import rnd.sueta.event_ms.service.business.EventRegistrationService;
import rnd.sueta.event_ms.service.entity.EventService;
import rnd.sueta.event_ms.util.EventScoreCalculator;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventCacheProviderImpl implements CacheProvider<EventWithPlace> {

    private final EventService eventService;
    private final EventRegistrationService eventRegistrationService;
    private final RedisGateway<EventWithPlace> eventRedisGateway;

    @Override
    public Page<EventWithPlace> getTop(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        long total = eventRedisGateway.getSetSize();

        if (total == 0L) {
            rebuildTop();
            total = eventRedisGateway.getSetSize();
        }

        long start = pageable.getOffset();
        long end = start + pageable.getPageSize() - 1;
        if (total == 0L || start >= total) {
            return getEmptyPage(total, pageable);
        }

        List<UUID> eventIds = eventRedisGateway.getOrderedKeys(start, end);
        List<EventWithPlace> events = eventRedisGateway.getAll(eventIds);

        return new PageImpl<>(events, pageable, total);
    }

    @Override
    public EventWithPlace getById(UUID placeId) {
        return eventRedisGateway.findById(placeId)
                .orElseGet(() -> eventService.getEventWithPlaceById(placeId));
    }

    @Override
    public EventWithPlace update(EventWithPlace event) {
        EventWithPlace updatedPlace = eventRegistrationService.updateEvent(event);
        EventScoreParts scoreParts = eventService.getScorePartsById(event.id());

        eventService.refreshTop();

        double score = EventScoreCalculator.calculateScore(scoreParts);
        double minScoreInSortedSet = eventRedisGateway.getMinScore();

        if (score > minScoreInSortedSet) {
            ItemWithScore<EventWithPlace> topEventWithScore = ItemWithScore.<EventWithPlace>builder()
                    .itemId(event.id())
                    .item(event)
                    .score(score)
                    .build();
            eventRedisGateway.cacheOne(topEventWithScore);
        }

        return updatedPlace;
    }

    @Override
    public void rebuildTop() {
        eventService.refreshTop();
        eventRedisGateway.deleteOldValues();

        List<ItemWithScore<EventWithPlace>> topEvents = eventService.getTopWithScores();
        if (topEvents.isEmpty()) {
            return;
        }

        eventRedisGateway.cacheAll(topEvents);
    }

    @Override
    public void delete(UUID id) {
        eventService.delete(id);
        eventRedisGateway.delete(id);
        eventService.refreshTop();
    }

    @Nullable
    private Page<EventWithPlace> getEmptyPage(long total, Pageable pageable) {
        if (total == 0L) {
            return new PageImpl<>(List.of(), pageable, 0L);
        }

        long start = pageable.getOffset();
        if (start >= total) {
            return new PageImpl<>(List.of(), pageable, total);
        }

        return null;
    }
}
