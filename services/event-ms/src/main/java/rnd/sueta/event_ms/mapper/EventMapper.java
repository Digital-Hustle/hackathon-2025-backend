package rnd.sueta.event_ms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import rnd.sueta.event_ms.config.BaseMapperConfig;
import rnd.sueta.event_ms.dto.EventDto;
import rnd.sueta.event_ms.dto.EventWithPlaceDto;
import rnd.sueta.event_ms.dto.request.CreateEventRq;
import rnd.sueta.event_ms.dto.request.UpdateEventRq;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.entity.Event;

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
}
