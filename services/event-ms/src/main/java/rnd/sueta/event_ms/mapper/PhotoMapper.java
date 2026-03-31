package rnd.sueta.event_ms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import rnd.sueta.event_ms.config.BaseMapperConfig;
import rnd.sueta.event_ms.dto.PhotoMetaDto;
import rnd.sueta.event_ms.model.PhotoWithUrl;
import rnd.sueta.event_ms.model.entity.PhotoMeta;

import java.util.List;

@Mapper(config = BaseMapperConfig.class)
public interface PhotoMapper {
    @Mapping(target = "url", ignore = true)
    PhotoMetaDto convert(PhotoMeta source);

    @Mapping(target = "ownerType", ignore = true)
    @Mapping(target = "extension", ignore = true)
    @Mapping(target = "fileSize", ignore = true)
    @Mapping(target = "contentType", ignore = true)
    PhotoMetaDto convert(PhotoWithUrl source);

    List<PhotoMetaDto> convert(List<PhotoWithUrl> source);

    default Page<PhotoMetaDto> convert(Page<PhotoWithUrl> source) {
        return source.map(this::convert);
    }
}
