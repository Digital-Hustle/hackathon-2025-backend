package rnd.sueta.event_ms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rnd.sueta.event_ms.config.BaseMapperConfig;
import rnd.sueta.event_ms.dto.PointDto;
import rnd.sueta.event_ms.model.entity.Point;

@Mapper(config = BaseMapperConfig.class)
public interface PointMapper {

    @Mapping(target = "id", ignore = true)
    Point convert(PointDto source);
}
