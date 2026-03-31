package rnd.sueta.event_ms.service.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rnd.sueta.event_ms.model.entity.Review;
import rnd.sueta.event_ms.service.business.ReviewRegistrator;
import rnd.sueta.event_ms.service.entity.EventService;
import rnd.sueta.event_ms.service.entity.ReviewService;

import java.util.UUID;

@Service("eventReviewRegistrator")
@RequiredArgsConstructor
public class EventReviewRegistratorImpl implements ReviewRegistrator {

    private final ReviewService eventReviewService;
    private final EventService eventService;

    @Override
    public Page<Review> getAllByOwnerId(UUID ownerId, int page, int size) {
        return eventReviewService.getAllByOwnerId(ownerId, page, size);
    }

    @Override
    public Review getById(UUID id) {
        return eventReviewService.getById(id);
    }

    @Transactional
    @Override
    public Review register(UUID ownerId, Review review) {
        Review createdReview = eventReviewService.create(ownerId, review);
        eventService.incrementRating(ownerId, createdReview.getRate());

        return createdReview;
    }

    @Transactional
    @Override
    public Review update(UUID ownerId, UUID reviewId, Review review) {
        Integer oldRate = eventReviewService.getRateById(reviewId);

        Review updatedReview = eventReviewService.update(
                ownerId,
                review.toBuilder()
                        .id(reviewId)
                        .build()
        );
        eventService.updateRating(ownerId, oldRate, updatedReview.getRate());

        return updatedReview;
    }

    @Transactional
    @Override
    public void delete(UUID ownerId, UUID reviewId) {
        Review dbReview = eventReviewService.getById(reviewId);
        Integer reviewRate = dbReview.getRate();

        eventReviewService.delete(reviewId);
        eventService.decrementRating(ownerId, reviewRate);
    }
}
