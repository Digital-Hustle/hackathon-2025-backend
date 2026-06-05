package rnd.sueta.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record PlaceScoreParts(

        Double totalRating,

        Integer reviewsAmount,

        Integer totalVisits,

        boolean recommended,

        Integer maxVisits
) {
}
