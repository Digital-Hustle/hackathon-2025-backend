package rnd.sueta.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import rnd.sueta.config.ThreadLocalMap;
import rnd.sueta.constants.ContextKeys;
import rnd.sueta.constants.ReviewErrorMessages;
import rnd.sueta.exception.custom.OwnerNotExists;
import rnd.sueta.model.entity.Review;
import rnd.sueta.repository.AbstractReviewRepository;
import rnd.sueta.service.entity.ReviewService;
import rnd.sueta.validator.ProfileValidator;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final AbstractReviewRepository abstractReviewRepository;

    @Override
    public Page<Review> getAllByOwnerId(UUID ownerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return abstractReviewRepository.findAllByOwnerId(ownerId, pageable);
    }

    @Override
    public Review getById(UUID id) {
        return abstractReviewRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Integer getRateById(UUID id) {
        return abstractReviewRepository.findRateById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @Override
    public Review create(UUID ownerId, Review review) {
        try {
            String profileId = ThreadLocalMap.get(ContextKeys.PROFILE_ID);

            ProfileValidator.checkProfileIdIsPresent(profileId);
            ProfileValidator.checkProfileIdIsUuid(profileId);

            Review preparedReview = review.toBuilder()
                    .id(UUID.randomUUID())
                    .profileId(UUID.fromString(profileId))
                    .createdAt(LocalDateTime.now())
                    .build();

            return abstractReviewRepository.save(ownerId, preparedReview);
        } catch (DataIntegrityViolationException exception) {
            throw new OwnerNotExists(ReviewErrorMessages.OWNER_NOT_EXISTS);
        }
    }

    @Transactional
    @Override
    public Review update(UUID ownerId, Review review) {
        Review dbReview = getById(review.getId());
        Review updatedReview = dbReview.toBuilder()
                .rate(review.getRate())
                .comment(review.getComment())
                .updatedAt(LocalDateTime.now())
                .build();

        return abstractReviewRepository.save(ownerId, updatedReview);
    }

    @Override
    public void delete(UUID id) {
        abstractReviewRepository.deleteById(id);
    }
}
