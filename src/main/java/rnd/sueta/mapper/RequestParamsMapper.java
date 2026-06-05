package rnd.sueta.mapper;

import org.mapstruct.Mapper;
import rnd.sueta.config.BaseMapperConfig;
import rnd.sueta.dto.params.EventsFilter;
import rnd.sueta.model.EventFilterParams;

@Mapper(config = BaseMapperConfig.class)
public interface RequestParamsMapper {

    EventFilterParams convert(EventsFilter eventsFilter, int page, int size);
}
