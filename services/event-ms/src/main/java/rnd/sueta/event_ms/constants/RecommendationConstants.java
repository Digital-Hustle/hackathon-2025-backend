package rnd.sueta.event_ms.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RecommendationConstants {

    public static final String PLACE_SCORES_MAT_VIEW_NAME = "place_scores";
    public static final String EVENT_SCORES_MAT_VIEW_NAME = "event_scores";

    public static final String REFRESH_MATERIALIZED_VIEW_CONCURRENTLY = "REFRESH MATERIALIZED VIEW CONCURRENTLY %s";
}
