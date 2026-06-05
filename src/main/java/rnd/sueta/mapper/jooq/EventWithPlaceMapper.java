package rnd.sueta.mapper.jooq;

import org.jooq.Record;
import org.jooq.generated.Tables;
import org.springframework.stereotype.Component;
import rnd.sueta.model.EventWithPlace;
import rnd.sueta.model.ItemWithScore;

import java.util.Objects;

@Component
public class EventWithPlaceMapper {

    public ItemWithScore<EventWithPlace> mapWithScore(Record record) {
        EventWithPlace event = record.into(EventWithPlace.class);
        Double score = record.get(Tables.EVENT_SCORES.SCORE, Double.class);

        return ItemWithScore.<EventWithPlace>builder()
                .itemId(event.id())
                .item(event)
                .score(Objects.isNull(score) ? 0.0 : score)
                .build();
    }
}
