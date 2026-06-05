package rnd.sueta.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rnd.sueta.model.EventScoreParts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventScoreCalculator {

    private static final double TIME_WEIGHT = 0.4;
    private static final double RATING_WEIGHT = 0.6;
    private static final double RECOMMENDATION_WEIGHT = 0.2;

    private static final int GLOBAL_MIN_REVIEWS = 5;
    private static final double GLOBAL_AVG_RATING = 4.0;

    private static final double LOGISTIC_STEEPNESS = 0.5;
    private static final double LOGISTIC_MIDPOINT_DAYS = 7.0;

    private static final double MIN_LIVE_FACTOR = 0.3;
    private static final int SCORE_SCALE = 4;
    private static final double RATING_NORMALIZATION_FACTOR = 4.0;

    public static double calculateScore(EventScoreParts scoreParts) {
        double ratingScore = calculateBayesianRating(scoreParts.totalRating(), scoreParts.reviewsAmount());
        double timeScore = calculateTimeRelevanceWithLogisticFormula(scoreParts.eventStart(), scoreParts.eventEnd());

        double weightedScore = (RATING_WEIGHT * ratingScore + TIME_WEIGHT * timeScore) / (RATING_WEIGHT + TIME_WEIGHT);
        double finalWeightedScore = weightedScore + (scoreParts.recommended() ? RECOMMENDATION_WEIGHT : 0.0);

        return BigDecimal.valueOf(finalWeightedScore)
                .setScale(SCORE_SCALE, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private static double calculateBayesianRating(Double totalRatingSum, Integer reviewsAmount) {
        double sum = Objects.requireNonNullElse(totalRatingSum, 0.0);
        int reviews = Objects.requireNonNullElse(reviewsAmount, 0);

        double smoothedSum = sum + GLOBAL_AVG_RATING * GLOBAL_MIN_REVIEWS;
        double smoothedCount = reviews + GLOBAL_MIN_REVIEWS;
        double bayesianAvg = smoothedSum / smoothedCount;

        return (bayesianAvg - 1.0) / RATING_NORMALIZATION_FACTOR;
    }

    /**
     * Логистическая функция — это S-образная кривая (сигмоида), описывающая рост, ограниченный сверху, где скорость
     * роста сначала увеличивается, а затем замедляется, стремясь к предельному значению.
     *
     * <p>За месяц вес низкий, за неделю — резко возрастает.</p>
     *
     * <p>
     * <img src="../../../../../../../../../docs/formulas/logistic_formula.png"
     * width="350"
     * alt="Haversinus formula example at project_root/docs/haversinus_formula.png"/>
     *
     * @param start event dateTime start
     * @param end   event dateTime end
     * @return double value calculated by formula
     */
    private static double calculateTimeRelevanceWithLogisticFormula(OffsetDateTime start, OffsetDateTime end) {
        Objects.requireNonNull(start, "start time should not be null");
        Objects.requireNonNull(end, "end time should not be null");

        OffsetDateTime now = OffsetDateTime.now();
        long totalDuration = Duration.between(start, end).toMinutes();

        if (end.isBefore(now) || totalDuration <= 0) {
            return 0.0;
        }

        if (start.isAfter(now)) {
            long daysUntilStart = ChronoUnit.DAYS.between(now, start);
            double xValue = LOGISTIC_STEEPNESS * (daysUntilStart - LOGISTIC_MIDPOINT_DAYS);

            return 1.0 / (1.0 + Math.exp(xValue));
        }

        long remaining = Duration.between(now, end).toMinutes();
        double remainingRatio = Math.max(0.0, remaining) / (double) totalDuration;
        return MIN_LIVE_FACTOR + (1.0 - MIN_LIVE_FACTOR) * remainingRatio;
    }
}
