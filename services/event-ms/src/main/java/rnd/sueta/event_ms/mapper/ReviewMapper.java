package rnd.sueta.event_ms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import rnd.sueta.event_ms.config.BaseMapperConfig;
import rnd.sueta.event_ms.dto.ReviewDto;
import rnd.sueta.event_ms.dto.request.CreateReviewRq;
import rnd.sueta.event_ms.dto.request.UpdateReviewRq;
import rnd.sueta.event_ms.model.entity.Review;

@Mapper(config = BaseMapperConfig.class)
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Review convert(CreateReviewRq createReviewRq);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Review convert(UpdateReviewRq updatereviewRq);

    ReviewDto convert(Review source);

    default Page<ReviewDto> convert(Page<Review> source) {
        return source.map(this::convert);
    }
}
