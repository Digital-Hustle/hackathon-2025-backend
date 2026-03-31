package rnd.sueta.event_ms.service.entity;

import org.springframework.data.domain.Page;
import rnd.sueta.event_ms.model.entity.Review;

import java.util.UUID;

public interface ReviewService {

    Page<Review> getAllByOwnerId(UUID ownerId, int page, int size);

    Review getById(UUID id);

    Integer getRateById(UUID id);

    Review create(UUID ownerId, Review review);

    Review update(UUID ownerId, Review review);

    void delete(UUID id);
}
