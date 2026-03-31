package rnd.sueta.event_ms.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlaceConstants {

    public static final int MAX_TITLE_LENGTH = 255;

    public static final int MAX_ADDRESS_LENGTH = 300;

    public static final int RATING_PRECISION = 2;
    public static final int RATING_SCALE = 1;

    public static final int COORDINATE_PRECISION = 9;
    public static final int COORDINATE_SCALE = 6;

    public static final String MIN_LATITUDE_VALUE = "-90";
    public static final String MAX_LATITUDE_VALUE = "90";
    public static final String MIN_LONGITUDE_VALUE = "-180";
    public static final String MAX_LONGITUDE_VALUE = "180";
}
