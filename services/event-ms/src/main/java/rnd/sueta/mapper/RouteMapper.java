package rnd.sueta.mapper;

import org.mapstruct.Mapper;
import rnd.sueta.config.BaseMapperConfig;
import rnd.sueta.dto.request.CreateRouteRq;
import rnd.sueta.dto.response.RouteWithDetailsRs;
import rnd.sueta.dto.response.RouteWithPlacesRs;
import rnd.sueta.model.RouteGenerationParams;
import rnd.sueta.model.RouteWithEvents;
import rnd.sueta.model.RouteWithPlaces;

@Mapper(config = BaseMapperConfig.class, uses = {PointMapper.class, PlaceMapper.class})
public interface RouteMapper {

    RouteWithPlacesRs convert(RouteWithPlaces source);

    RouteWithDetailsRs convert(RouteWithEvents source);

    RouteGenerationParams convert(CreateRouteRq source);
}
