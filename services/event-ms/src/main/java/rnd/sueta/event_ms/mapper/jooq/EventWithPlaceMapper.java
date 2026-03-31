package rnd.sueta.event_ms.mapper.jooq;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.generated.Tables;
import org.springframework.stereotype.Component;
import rnd.sueta.event_ms.enums.EventType;
import rnd.sueta.event_ms.model.EventWithPlace;

@Component
public class EventWithPlaceMapper implements RecordMapper<Record, EventWithPlace> {
    @Override
    public EventWithPlace map(Record record) {
        return EventWithPlace.builder()
                .eventId(record.get(Tables.EVENTS.ID))
                .placeId(record.get(Tables.PLACES.ID))
                .latitude(record.get(Tables.POINTS.LATITUDE))
                .longitude(record.get(Tables.POINTS.LONGITUDE))
                .title(record.get(Tables.EVENTS.TITLE))
                .eventType(EventType.valueOf(record.get(Tables.EVENTS.TYPE)))
                .totalRating(record.get(Tables.PLACES.TOTAL_RATING))
                .reviewsAmount(record.get(Tables.PLACES.REVIEWS_AMOUNT))
                .eventStart(record.get(Tables.EVENTS.EVENT_START))
                .eventEnd(record.get(Tables.EVENTS.EVENT_END))
                .price(record.get(Tables.EVENTS.PRICE))
                .ageRestriction(record.get(Tables.EVENTS.AGE_RESTRICTION))
                .build();
    }
}
