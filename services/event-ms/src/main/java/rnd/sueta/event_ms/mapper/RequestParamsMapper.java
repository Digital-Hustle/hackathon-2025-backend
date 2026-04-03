package rnd.sueta.event_ms.mapper;

import org.mapstruct.Mapper;
import rnd.sueta.event_ms.config.BaseMapperConfig;
import rnd.sueta.event_ms.dto.params.EventsFilter;
import rnd.sueta.event_ms.model.EventFilterParams;

@Mapper(config = BaseMapperConfig.class)
public interface RequestParamsMapper {

    EventFilterParams convert(EventsFilter eventsFilter, int page, int size);
}
