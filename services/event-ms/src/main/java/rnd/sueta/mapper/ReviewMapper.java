package rnd.sueta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import rnd.sueta.config.BaseMapperConfig;
import rnd.sueta.dto.ReviewDto;
import rnd.sueta.dto.request.CreateReviewRq;
import rnd.sueta.dto.request.UpdateReviewRq;
import rnd.sueta.model.entity.Review;

import java.util.UUID;

@Mapper(config = BaseMapperConfig.class)
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Review convert(CreateReviewRq createReviewRq);

    @Mapping(target = "profileId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Review convert(UUID id, UpdateReviewRq updatereviewRq);

    ReviewDto convert(Review source);

    default Page<ReviewDto> convert(Page<Review> source) {
        return source.map(this::convert);
    }
}
