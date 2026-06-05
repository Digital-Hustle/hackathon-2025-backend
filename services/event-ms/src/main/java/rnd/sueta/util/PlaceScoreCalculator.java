package rnd.sueta.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rnd.sueta.model.PlaceScoreParts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlaceScoreCalculator {

    private static final double RATING_WEIGHT = 0.6;
    private static final double POPULARITY_WEIGHT = 0.4;
    private static final double RECOMMENDATION_WEIGHT = 0.2;

    private static final int BAYESIAN_M = 10;
    private static final double MIN_RATING = 1.0;
    private static final double RATING_RANGE = 4.0;
    private static final double DEFAULT_RATING_MEAN = 4.0;

    private static final int SCALE = 6;

    public static double calculateScore(PlaceScoreParts scoreParts) {
        double bayesianAverage = calculateBayesianAverage(scoreParts.totalRating(), scoreParts.reviewsAmount());
        double popularityPart = calculatePopularity(scoreParts.maxVisits(), scoreParts.totalVisits());
        double bonus = 1.0 + (scoreParts.recommended() ? RECOMMENDATION_WEIGHT : 0);

        double scoreWithBonus = (bayesianAverage + popularityPart) * bonus;

        BigDecimal finalScore = BigDecimal.valueOf(scoreWithBonus)
                .setScale(SCALE, RoundingMode.HALF_UP);
        return finalScore.doubleValue();
    }

    private static double calculateBayesianAverage(Double totalRating, Integer reviewsAmount) {
        double ratingSum = Objects.requireNonNullElse(totalRating, 0.0) + BAYESIAN_M * DEFAULT_RATING_MEAN;
        double reviewCount = Objects.requireNonNullElse(reviewsAmount, 0) + BAYESIAN_M;

        double bayesianMean = ratingSum / reviewCount;
        double normalized = (bayesianMean - MIN_RATING) / RATING_RANGE;

        return normalized * RATING_WEIGHT;
    }

    private static double calculatePopularity(Integer maxVisits, Integer totalVisits) {
        if (maxVisits != null && maxVisits > 0 && totalVisits != null) {
            return ((double) totalVisits / maxVisits) * POPULARITY_WEIGHT;
        }
        return 0.0;
    }
}
