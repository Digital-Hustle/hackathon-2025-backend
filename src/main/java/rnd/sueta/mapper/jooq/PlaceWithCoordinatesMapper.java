package rnd.sueta.mapper.jooq;

import org.jooq.Record;
import org.jooq.generated.Tables;
import org.springframework.stereotype.Component;
import rnd.sueta.model.ItemWithScore;
import rnd.sueta.model.PlaceWithCoordinates;

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
