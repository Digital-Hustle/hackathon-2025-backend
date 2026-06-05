package rnd.sueta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import rnd.sueta.config.BaseMapperConfig;
import rnd.sueta.dto.EventDto;
import rnd.sueta.dto.EventWithPlaceDto;
import rnd.sueta.dto.request.CreateEventRq;
import rnd.sueta.dto.request.UpdateEventRq;
import rnd.sueta.model.EventWithPlace;
import rnd.sueta.model.entity.Event;

import java.util.UUID;

@Mapper(config = BaseMapperConfig.class)
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "recommended", ignore = true)
    Event convert(CreateEventRq source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "recommended", ignore = true)
    Event convert(UpdateEventRq source);

    EventDto convert(Event source);

    EventWithPlaceDto convert(EventWithPlace source);

    default Page<EventWithPlaceDto> convert(Page<EventWithPlace> source) {
        return source.map(this::convert);
    }

    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "totalRating", ignore = true)
    @Mapping(target = "reviewsAmount", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    EventWithPlace convertWithPlace(UUID id, UpdateEventRq source);
}
