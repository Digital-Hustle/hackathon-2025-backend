package rnd.sueta.event_ms.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventConstants {

    public static final int MAX_TITLE_LENGTH = 255;

    public static final int RATING_PRECISION = 2;
    public static final int RATING_SCALE = 1;

    public static final String MIN_PRICE_VALUE = "0.0";
    public static final int PRICE_PRECISION = 10;
    public static final int PRICE_SCALE = 2;

    public static final int MIN_AGE_VALUE = 0;

    public static final int ONE_DAY_AFTER_START = 1;
}
