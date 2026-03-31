package rnd.sueta.event_ms.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public final class RatingHelper {

    private static final int TEN_INT = 10;
    private static final double TEN_DOUBLE = 10.0;

    public double countAverage(Integer totalRating, Integer reviewsAmount) {
        boolean reviewsAmountIsZero = Objects.isNull(reviewsAmount) || reviewsAmount == 0;
        if (reviewsAmountIsZero || Objects.isNull(totalRating)) {
            return 0.0;
        }

        double averageRating = (double) totalRating / reviewsAmount;
        return Math.round(averageRating * TEN_INT) / TEN_DOUBLE;
    }
}
