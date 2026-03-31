package rnd.sueta.event_ms.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.Tables;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import rnd.sueta.event_ms.enums.EventType;
import rnd.sueta.event_ms.mapper.jooq.EventWithPlaceMapper;
import rnd.sueta.event_ms.model.DateTimeRange;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.entity.Event;
import rnd.sueta.event_ms.model.entity.Point;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EventRepository {

    private final DSLContext dsl;
    private final EventWithPlaceMapper eventWithPlaceMapper;

    public Page<EventWithPlace> findEventsWithPlaceByDate(DateTimeRange range, Pageable pageable) {
        Long total = dsl.selectCount()
                .from(Tables.EVENTS)
                .where(Tables.EVENTS.EVENT_START.between(range.start(), range.end()))
                .fetchOneInto(Long.class);

        List<EventWithPlace> events = dsl.select()
                .from(Tables.EVENTS)
                .join(Tables.PLACES).on(Tables.PLACES.ID.eq(Tables.EVENTS.PLACE_ID))
                .join(Tables.POINTS).on(Tables.POINTS.ID.eq(Tables.PLACES.POINT_ID))
                .where(Tables.EVENTS.EVENT_START.between(range.start(), range.end()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .map(eventWithPlaceMapper);

        return new PageImpl<>(events, pageable, Objects.requireNonNullElse(total, 0L));
    }

    public Page<EventWithPlace> findEventsWithPlaceByDateAndCategories(
            List<EventType> categories,
            DateTimeRange range,
            Pageable pageable
    ) {
        Long total = dsl.selectCount()
                .from(Tables.EVENTS)
                .where(Tables.EVENTS.EVENT_START.between(range.start(), range.end()))
                .and(Tables.EVENTS.TYPE.in(categories))
                .fetchOneInto(Long.class);

        List<EventWithPlace> events = dsl.select()
                .from(Tables.EVENTS)
                .join(Tables.PLACES).on(Tables.PLACES.ID.eq(Tables.EVENTS.PLACE_ID))
                .join(Tables.POINTS).on(Tables.POINTS.ID.eq(Tables.PLACES.POINT_ID))
                .where(Tables.EVENTS.EVENT_START.between(range.start(), range.end()))
                .and(Tables.EVENTS.TYPE.in(categories))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .map(eventWithPlaceMapper);

        return new PageImpl<>(events, pageable, Objects.requireNonNullElse(total, 0L));
    }

    public List<EventWithPlace> findAllInRangeByCategories(
            Point startPoint, Point endPoint,
            List<EventType> categories
    ) {
        return dsl.select()
                .from(Tables.EVENTS)
                .join(Tables.PLACES).on(Tables.PLACES.ID.eq(Tables.EVENTS.PLACE_ID))
                .join(Tables.POINTS).on(Tables.POINTS.ID.eq(Tables.PLACES.POINT_ID))
                .where(Tables.POINTS.LATITUDE.between(startPoint.getLatitude(), endPoint.getLatitude()))
                .and(Tables.POINTS.LONGITUDE.between(startPoint.getLongitude(), endPoint.getLongitude()))
                .and(Tables.EVENTS.TYPE.in(categories))
                .fetch()
                .map(eventWithPlaceMapper);
    }

    public Optional<EventWithPlace> findEventWithPlaceByEventId(UUID id) {
        return dsl.select()
                .from(Tables.EVENTS)
                .join(Tables.PLACES).on(Tables.PLACES.ID.eq(Tables.EVENTS.PLACE_ID))
                .join(Tables.POINTS).on(Tables.POINTS.ID.eq(Tables.PLACES.POINT_ID))
                .where(Tables.EVENTS.ID.eq(id))
                .fetchOptional()
                .map(eventWithPlaceMapper);
    }

    public Event save(Event event) {
        return dsl.insertInto(Tables.EVENTS)
                .set(dsl.newRecord(Tables.EVENTS, event))
                .onConflict(Tables.EVENTS.ID)
                .doUpdate()
                .set(dsl.newRecord(Tables.EVENTS, event))
                .returning()
                .fetchOne(jooqRecord -> jooqRecord.into(Event.class));
    }

    public void incrementRating(UUID id, Integer newRate) {
        dsl.update(Tables.EVENTS)
                .set(Tables.EVENTS.TOTAL_RATING, Tables.EVENTS.TOTAL_RATING.plus(newRate))
                .set(Tables.EVENTS.REVIEWS_AMOUNT, Tables.EVENTS.REVIEWS_AMOUNT.plus(1))
                .where(Tables.EVENTS.ID.eq(id))
                .execute();
    }

    public void updateRating(UUID id, Integer oldRate, Integer newRate) {
        dsl.update(Tables.EVENTS)
                .set(Tables.EVENTS.TOTAL_RATING, Tables.EVENTS.TOTAL_RATING.minus(oldRate).plus(newRate))
                .where(Tables.EVENTS.ID.eq(id))
                .execute();
    }

    public void decrementRating(UUID id, Integer oldRate) {
        dsl.update(Tables.EVENTS)
                .set(Tables.EVENTS.TOTAL_RATING, Tables.EVENTS.TOTAL_RATING.minus(oldRate))
                .set(Tables.EVENTS.REVIEWS_AMOUNT, Tables.EVENTS.REVIEWS_AMOUNT.minus(1))
                .where(Tables.EVENTS.ID.eq(id))
                .execute();
    }

    public void deleteById(UUID id) {
        dsl.deleteFrom(Tables.EVENTS)
                .where(Tables.EVENTS.ID.eq(id))
                .execute();
    }

    public boolean existsById(UUID id) {
        return dsl.fetchExists(dsl.selectOne()
                .from(Tables.EVENTS)
                .where(Tables.EVENTS.ID.eq(id)));
    }
}
