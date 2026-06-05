package rnd.sueta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rnd.sueta.config.BaseMapperConfig;
import rnd.sueta.dto.PointDto;
import rnd.sueta.model.entity.Point;

@Mapper(config = BaseMapperConfig.class)
public interface PointMapper {

    @Mapping(target = "id", ignore = true)
    Point convert(PointDto source);
}
