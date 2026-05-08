package rnd.sueta.event_ms.mapper.jooq;

import org.jooq.Record;
import org.jooq.generated.Tables;
import org.springframework.stereotype.Component;
import rnd.sueta.event_ms.model.ItemWithScore;
import rnd.sueta.event_ms.model.PlaceWithCoordinates;

import java.util.Objects;

@Component
public class PlaceWithCoordinatesMapper {

    public ItemWithScore<PlaceWithCoordinates> mapWithScore(Record record) {
        PlaceWithCoordinates place = record.into(PlaceWithCoordinates.class);
        Double score = record.get(Tables.PLACE_SCORES.SCORE, Double.class);

        return ItemWithScore.<PlaceWithCoordinates>builder()
                .itemId(place.id())
                .score(Objects.isNull(score) ? 0.0 : score)
                .item(place)
                .build();
    }
}
