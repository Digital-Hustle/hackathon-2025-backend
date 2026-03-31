package rnd.sueta.event_ms.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.Tables;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import rnd.sueta.event_ms.model.JoinTable;
import rnd.sueta.event_ms.model.entity.Review;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class AbstractReviewRepository {

    private final DSLContext dsl;
    private final JoinTable joinTable;

    public Page<Review> findAllByOwnerId(UUID ownerId, Pageable pageable) {
        List<Review> reviews = dsl.select(Tables.REVIEWS)
                .from(Tables.REVIEWS)
                .join(joinTable.table())
                .on(joinTable.linkedTableId().eq(Tables.REVIEWS.ID))
                .where(joinTable.ownerTableId().eq(ownerId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(Review.class);

        long total = countOwnerReviews(ownerId);

        return new PageImpl<>(reviews, pageable, total);
    }

    public Optional<Review> findById(UUID id) {
        return dsl.select()
                .from(Tables.REVIEWS)
                .where(Tables.REVIEWS.ID.eq(id))
                .fetchOptionalInto(Review.class);
    }

    public Optional<Integer> findRateById(UUID id) {
        return dsl.select(Tables.REVIEWS.RATE)
                .from(Tables.REVIEWS)
                .where(Tables.REVIEWS.ID.eq(id))
                .fetchOptionalInto(Integer.class);
    }

    public Review save(UUID ownerId, Review review) {
        Review savedReview = dsl.insertInto(Tables.REVIEWS)
                .set(dsl.newRecord(Tables.REVIEWS, review))
                .onConflict(Tables.REVIEWS.ID)
                .doUpdate()
                .set(dsl.newRecord(Tables.REVIEWS, review))
                .returning()
                .fetchOne(jooqRecord -> jooqRecord.into(Review.class));

        dsl.insertInto(joinTable.table())
                .set(joinTable.linkedTableId(), review.getId())
                .set(joinTable.ownerTableId(), ownerId)
                .onConflict()
                .doNothing()
                .execute();

        return savedReview;
    }

    public void deleteById(UUID id) {
        dsl.deleteFrom(Tables.REVIEWS)
                .where(Tables.REVIEWS.ID.eq(id))
                .execute();
    }

    private Long countOwnerReviews(UUID ownerId) {
        Long total = dsl.selectCount()
                .from(Tables.REVIEWS)
                .join(joinTable.table())
                .on(joinTable.linkedTableId().eq(Tables.REVIEWS.ID))
                .where(joinTable.ownerTableId().eq(ownerId))
                .fetchOneInto(Long.class);

        return Objects.requireNonNullElse(total, 0L);
    }
}
