package rnd.sueta.event_ms.service.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rnd.sueta.event_ms.model.entity.Review;
import rnd.sueta.event_ms.service.business.ReviewRegistrator;
import rnd.sueta.event_ms.service.entity.PlaceService;
import rnd.sueta.event_ms.service.entity.ReviewService;

import java.util.UUID;

@Service("placeReviewRegistrator")
@RequiredArgsConstructor
public class PlaceReviewRegistratorImpl implements ReviewRegistrator {

    private final ReviewService placeReviewService;
    private final PlaceService placeService;

    @Override
    public Page<Review> getAllByOwnerId(UUID ownerId, int page, int size) {
        return placeReviewService.getAllByOwnerId(ownerId, page, size);
    }

    @Override
    public Review getById(UUID id) {
        return placeReviewService.getById(id);
    }

    @Transactional
    @Override
    public Review register(UUID ownerId, Review review) {
        Review createdReview = placeReviewService.create(ownerId, review);
        placeService.incrementRating(ownerId, createdReview.getRate());

        return createdReview;
    }

    @Transactional
    @Override
    public Review update(UUID ownerId, UUID reviewId, Review review) {
        Integer oldRate = placeReviewService.getRateById(reviewId);

        Review updatedReview = placeReviewService.update(
                ownerId,
                review.toBuilder()
                        .id(reviewId)
                        .build()
        );
        placeService.updateRating(ownerId, oldRate, updatedReview.getRate());

        return updatedReview;
    }

    @Transactional
    @Override
    public void delete(UUID ownerId, UUID reviewId) {
        Review dbReview = placeReviewService.getById(reviewId);
        Integer reviewRate = dbReview.getRate();

        placeReviewService.delete(reviewId);
        placeService.decrementRating(ownerId, reviewRate);
    }
}
