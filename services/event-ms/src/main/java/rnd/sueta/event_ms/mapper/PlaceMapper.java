package rnd.sueta.event_ms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import rnd.sueta.event_ms.config.BaseMapperConfig;
import rnd.sueta.event_ms.dto.PlaceDto;
import rnd.sueta.event_ms.dto.request.CreatePlaceRq;
import rnd.sueta.event_ms.dto.request.UpdatePlaceRq;
import rnd.sueta.event_ms.model.PlaceWithCoordinates;

import java.util.List;

@Mapper(config = BaseMapperConfig.class)
public interface PlaceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pointId", ignore = true)
    @Mapping(target = "totalRating", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    @Mapping(target = "reviewsAmount", ignore = true)
    @Mapping(target = "totalVisits", ignore = true)
    @Mapping(target = "contacts.mobileNumbers", source = "mobileNumbers")
    @Mapping(target = "contacts.email", source = "email")
    @Mapping(target = "contacts.websiteUrl", source = "websiteUrl")
    @Mapping(target = "contacts.social", source = "social")
    PlaceWithCoordinates convert(CreatePlaceRq source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pointId", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "totalRating", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    @Mapping(target = "reviewsAmount", ignore = true)
    @Mapping(target = "totalVisits", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    @Mapping(target = "latitude", ignore = true)
    PlaceWithCoordinates convert(UpdatePlaceRq source);

    @Mapping(target = "mobileNumbers", source = "contacts.mobileNumbers")
    @Mapping(target = "email", source = "contacts.email")
    @Mapping(target = "websiteUrl", source = "contacts.websiteUrl")
    @Mapping(target = "social", source = "contacts.social")
    PlaceDto convert(PlaceWithCoordinates place);

    List<PlaceDto> convert(List<PlaceWithCoordinates> source);

    default Page<PlaceDto> convert(Page<PlaceWithCoordinates> source) {
        return source.map(this::convert);
    }
}
