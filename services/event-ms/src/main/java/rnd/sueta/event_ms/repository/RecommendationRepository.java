package rnd.sueta.event_ms.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.Tables;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import rnd.sueta.event_ms.constants.RecommendationConstants;
import rnd.sueta.event_ms.mapper.jooq.EventWithPlaceMapper;
import rnd.sueta.event_ms.mapper.jooq.PlaceWithCoordinatesMapper;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.PlaceWithCoordinates;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class RecommendationRepository {

    private final DSLContext dsl;
    private final PlaceWithCoordinatesMapper placeWithCoordinatesMapper;
    private final EventWithPlaceMapper eventWithPlaceMapper;

    public Page<PlaceWithCoordinates> findAllPlaces(Pageable pageable) {
        long total = dsl.fetchCount(Tables.PLACES);

        List<PlaceWithCoordinates> places = dsl.select()
                .from(Tables.PLACES)
                .join(Tables.POINTS)
                .on(Tables.POINTS.ID.eq(Tables.PLACES.POINT_ID))
                .join(Tables.PLACE_SCORES)
                .on(Tables.PLACES.ID.eq(Tables.PLACE_SCORES.PLACE_ID))
                .orderBy(Tables.PLACE_SCORES.SCORE.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .map(placeWithCoordinatesMapper);

        return new PageImpl<>(places, pageable, total);
    }

    public Page<EventWithPlace> findAllEvents(Pageable pageable) {
        Long total = dsl.selectCount()
                .from(Tables.EVENTS)
                .fetchOneInto(Long.class);

        List<EventWithPlace> events = dsl.select()
                .from(Tables.EVENTS)
                .join(Tables.PLACES).on(Tables.PLACES.ID.eq(Tables.EVENTS.PLACE_ID))
                .join(Tables.POINTS).on(Tables.POINTS.ID.eq(Tables.PLACES.POINT_ID))
                .join(Tables.EVENT_SCORES).on(Tables.PLACES.ID.eq(Tables.EVENT_SCORES.EVENT_ID))
                .orderBy(Tables.EVENT_SCORES.SCORE.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .map(eventWithPlaceMapper);

        return new PageImpl<>(events, pageable, Objects.requireNonNullElse(total, 0L));
    }

    public void refreshPlaces() {
        dsl.execute(RecommendationConstants.REFRESH_MATERIALIZED_VIEW_CONCURRENTLY
                .formatted(RecommendationConstants.PLACE_SCORES_MAT_VIEW_NAME));
    }

    public void refreshEvents() {
        dsl.execute(RecommendationConstants.REFRESH_MATERIALIZED_VIEW_CONCURRENTLY
                .formatted(RecommendationConstants.EVENT_SCORES_MAT_VIEW_NAME));
    }
}
