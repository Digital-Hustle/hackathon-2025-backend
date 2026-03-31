package rnd.sueta.event_ms.service.business;

import org.springframework.data.domain.Page;
import rnd.sueta.event_ms.model.entity.Review;

import java.util.UUID;

public interface ReviewRegistrator {

    Page<Review> getAllByOwnerId(UUID ownerId, int page, int size);

    Review getById(UUID id);

    Review register(UUID ownerId, Review review);

    Review update(UUID ownerId, UUID reviewId, Review review);

    void delete(UUID ownerId, UUID reviewId);
}
