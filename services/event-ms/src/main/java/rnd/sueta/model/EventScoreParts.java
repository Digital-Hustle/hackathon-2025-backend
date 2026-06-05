package rnd.sueta.model;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder(toBuilder = true)
public record EventScoreParts(

        Double totalRating,

        Integer reviewsAmount,

        OffsetDateTime eventStart,

        OffsetDateTime eventEnd,

        boolean recommended,

        Integer maxReviews
) {
}
