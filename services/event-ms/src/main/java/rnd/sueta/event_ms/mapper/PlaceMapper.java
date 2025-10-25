package rnd.sueta.event_ms.mapper;

import org.mapstruct.Mapper;
import rnd.sueta.event_ms.dto.PlaceDto;
import rnd.sueta.event_ms.model.Place;

@Mapper(componentModel = "spring", uses = EventMapper.class)
public interface PlaceMapper {
    Place convert(PlaceDto source);

    PlaceDto convert(Place source);
}
