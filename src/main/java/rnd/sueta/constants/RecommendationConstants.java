package rnd.sueta.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RecommendationConstants {

    public static final String PLACE_SCORES_MAT_VIEW_NAME = "place_scores";
    public static final String EVENT_SCORES_MAT_VIEW_NAME = "event_scores";

    public static final String REFRESH_MATERIALIZED_VIEW_CONCURRENTLY = "REFRESH MATERIALIZED VIEW CONCURRENTLY %s";

    public static final int TOP_SIZE = 100;

    public static final Duration TOP_PLACES_TTL_DAYS = Duration.ofDays(1);
    public static final String TOP_PLACES_ZSET_KEY = "topPlaces:zset";
    public static final String TOP_PLACE_VALUE_KEY_PREFIX = "topPlaces:place:";

    public static final Duration TOP_EVENTS_TTL_DAYS = Duration.ofDays(1);
    public static final String TOP_EVENTS_ZSET_KEY = "topEvents:zset";
    public static final String TOP_EVENT_VALUE_KEY_PREFIX = "topEvents:events:";
}
