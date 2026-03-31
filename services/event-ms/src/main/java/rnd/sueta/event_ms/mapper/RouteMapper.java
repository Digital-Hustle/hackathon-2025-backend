package rnd.sueta.event_ms.mapper;

import org.mapstruct.Mapper;
import rnd.sueta.event_ms.config.BaseMapperConfig;
import rnd.sueta.event_ms.dto.request.CreateRouteRq;
import rnd.sueta.event_ms.dto.response.RouteWithDetailsRs;
import rnd.sueta.event_ms.dto.response.RouteWithPlacesRs;
import rnd.sueta.event_ms.model.RouteGenerationParams;
import rnd.sueta.event_ms.model.RouteWithEvents;
import rnd.sueta.event_ms.model.RouteWithPlaces;

@Mapper(config = BaseMapperConfig.class, uses = {PointMapper.class, PlaceMapper.class})
public interface RouteMapper {

    RouteWithPlacesRs convert(RouteWithPlaces source);

    RouteWithDetailsRs convert(RouteWithEvents source);

    RouteGenerationParams convert(CreateRouteRq source);
}
