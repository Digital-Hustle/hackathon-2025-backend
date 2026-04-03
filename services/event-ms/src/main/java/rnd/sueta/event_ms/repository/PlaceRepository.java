package rnd.sueta.event_ms.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.generated.Tables;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import rnd.sueta.event_ms.constants.RecommendationConstants;
import rnd.sueta.event_ms.mapper.jooq.PlaceWithCoordinatesMapper;
import rnd.sueta.event_ms.model.ItemWithScore;
import rnd.sueta.event_ms.model.PlaceScoreParts;
import rnd.sueta.event_ms.model.PlaceWithCoordinates;
import rnd.sueta.event_ms.model.entity.Point;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PlaceRepository {

    private final DSLContext dsl;
    private final PlaceWithCoordinatesMapper placeWithCoordinatesMapper;

    public Page<PlaceWithCoordinates> findAll(Pageable pageable) {
        long total = dsl.fetchCount(Tables.PLACES);

        List<PlaceWithCoordinates> places = dsl.select()
                .from(Tables.PLACES)
                .join(Tables.POINTS)
                .on(Tables.POINTS.ID.eq(Tables.PLACES.POINT_ID))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(PlaceWithCoordinates.class);

        return new PageImpl<>(places, pageable, total);
    }

    public List<ItemWithScore<PlaceWithCoordinates>> findTopWithScores() {
        return dsl.select()
                .from(Tables.PLACES)
                .join(Tables.POINTS)
                .on(Tables.POINTS.ID.eq(Tables.PLACES.POINT_ID))
                .join(Tables.PLACE_SCORES)
                .on(Tables.PLACES.ID.eq(Tables.PLACE_SCORES.PLACE_ID))
                .orderBy(Tables.PLACE_SCORES.SCORE.desc())
                .limit(RecommendationConstants.TOP_SIZE)
                .fetch(placeWithCoordinatesMapper::mapWithScore);
    }

    public List<PlaceWithCoordinates> findAllByRouteId(UUID routeId) {
        return dsl.select()
                .from(Tables.PLACES)
                .join(Tables.ROUTE_PLACES)
                .on(Tables.PLACES.ID.eq(Tables.ROUTE_PLACES.PLACE_ID))
                .join(Tables.POINTS)
                .on(Tables.POINTS.ID.eq(Tables.PLACES.POINT_ID))
                .where(Tables.ROUTE_PLACES.ROUTE_ID.eq(routeId))
                .fetchInto(PlaceWithCoordinates.class);
    }

    public Optional<PlaceWithCoordinates> findById(UUID id) {
        return dsl.select()
                .from(Tables.PLACES)
                .join(Tables.POINTS)
                .on(Tables.POINTS.ID.eq(Tables.PLACES.POINT_ID))
                .where(Tables.PLACES.ID.eq(id))
                .fetchOptionalInto(PlaceWithCoordinates.class);
    }

    public PlaceScoreParts findScoreParts(UUID id) {
        Field<Integer> maxVisits = DSL.field(DSL.select(DSL.coalesce(DSL.max(Tables.PLACES.TOTAL_VISITS), 0))
                .from(Tables.PLACES)).as("maxVisits");

        return dsl.select(
                        Tables.PLACES.TOTAL_RATING,
                        Tables.PLACES.REVIEWS_AMOUNT,
                        Tables.PLACES.TOTAL_VISITS,
                        Tables.PLACES.RECOMMENDED,
                        maxVisits)
                .from(Tables.PLACES)
                .where(Tables.PLACES.ID.eq(id))
                .fetchOneInto(PlaceScoreParts.class);
    }

    public boolean existsById(UUID id) {
        return dsl.fetchExists(dsl.selectOne()
                .from(Tables.PLACES)
                .where(Tables.PLACES.ID.eq(id)));
    }

    public PlaceWithCoordinates save(PlaceWithCoordinates place) {
        Point savedPoint = insertPoint(place);

        if (savedPoint == null) {
            savedPoint = getPoint(place.latitude(), place.longitude());
        }

        PlaceWithCoordinates placeWithSavedPoint = place.toBuilder()
                .pointId(savedPoint.getId())
                .build();

        dsl.insertInto(Tables.PLACES)
                .set(dsl.newRecord(Tables.PLACES, placeWithSavedPoint))
                .onConflict(Tables.PLACES.ID)
                .doUpdate()
                .set(dsl.newRecord(Tables.PLACES, placeWithSavedPoint))
                .execute();

        return place;
    }

    public void refreshTopPlaces() {
        dsl.execute(RecommendationConstants.REFRESH_MATERIALIZED_VIEW_CONCURRENTLY
                .formatted(RecommendationConstants.PLACE_SCORES_MAT_VIEW_NAME));
    }

    public void incrementRating(UUID id, Integer newRate) {
        dsl.update(Tables.PLACES)
                .set(Tables.PLACES.TOTAL_RATING, Tables.PLACES.TOTAL_RATING.plus(newRate))
                .set(Tables.PLACES.REVIEWS_AMOUNT, Tables.PLACES.REVIEWS_AMOUNT.plus(1))
                .where(Tables.PLACES.ID.eq(id))
                .execute();
    }

    public void updateRating(UUID id, Integer oldRate, Integer newRate) {
        dsl.update(Tables.PLACES)
                .set(Tables.PLACES.TOTAL_RATING, Tables.PLACES.TOTAL_RATING.minus(oldRate).plus(newRate))
                .where(Tables.PLACES.ID.eq(id))
                .execute();
    }

    public void decrementRating(UUID id, Integer oldRate) {
        dsl.update(Tables.PLACES)
                .set(Tables.PLACES.TOTAL_RATING, Tables.PLACES.TOTAL_RATING.minus(oldRate))
                .set(Tables.PLACES.REVIEWS_AMOUNT, Tables.PLACES.REVIEWS_AMOUNT.minus(1))
                .where(Tables.PLACES.ID.eq(id))
                .execute();
    }

    public void deleteById(UUID id) {
        dsl.deleteFrom(Tables.PLACES)
                .where(Tables.PLACES.ID.eq(id))
                .execute();
    }

    private Point insertPoint(PlaceWithCoordinates place) {
        Point point = Point.builder()
                .id(UUID.randomUUID())
                .latitude(place.latitude())
                .longitude(place.longitude())
                .build();

        return dsl.insertInto(Tables.POINTS)
                .set(dsl.newRecord(Tables.POINTS, point))
                .onConflict()
                .doNothing()
                .returning()
                .fetchOneInto(Point.class);
    }

    private Point getPoint(BigDecimal latitude, BigDecimal longitude) {
        return dsl.select()
                .from(Tables.POINTS)
                .where(Tables.POINTS.LATITUDE.eq(latitude))
                .and(Tables.POINTS.LONGITUDE.eq(longitude))
                .fetchOneInto(Point.class);
    }
}
