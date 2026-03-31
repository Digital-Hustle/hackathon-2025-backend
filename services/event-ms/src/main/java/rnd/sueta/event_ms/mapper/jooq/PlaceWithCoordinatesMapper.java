package rnd.sueta.event_ms.mapper.jooq;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.generated.Tables;
import org.springframework.stereotype.Component;
import rnd.sueta.event_ms.enums.PlaceType;
import rnd.sueta.event_ms.model.PlaceWithCoordinates;

@Component
public class PlaceWithCoordinatesMapper implements RecordMapper<Record, PlaceWithCoordinates> {

    @Override
    public PlaceWithCoordinates map(Record record) {
        return PlaceWithCoordinates.builder()
                .id(record.get(Tables.PLACES.ID))
                .title(record.get(Tables.PLACES.TITLE))
                .address(record.get(Tables.PLACES.ADDRESS))
                .type(PlaceType.valueOf(record.get(Tables.PLACES.TYPE)))
                .totalRating(record.get(Tables.PLACES.TOTAL_RATING))
                .reviewsAmount(record.get(Tables.PLACES.REVIEWS_AMOUNT))
                .totalVisits(record.get(Tables.PLACES.TOTAL_VISITS))
                .ownerId(record.get(Tables.PLACES.OWNER_ID))
                .contacts(record.get(Tables.PLACES.CONTACTS))
                .pointId(record.get(Tables.POINTS.ID))
                .latitude(record.get(Tables.POINTS.LATITUDE))
                .longitude(record.get(Tables.POINTS.LONGITUDE))
                .build();
    }
}
