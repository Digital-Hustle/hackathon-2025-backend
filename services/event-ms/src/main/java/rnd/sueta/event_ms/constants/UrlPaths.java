package rnd.sueta.event_ms.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlPaths {

    public static final String API = "/api";
    public static final String API_VERSION = API + "/v1";

    public static final String PLACES = API_VERSION + "/places";
    public static final String EVENTS = API_VERSION + "/events";

    public static final String ROUTES = API_VERSION + "/routes";
    public static final String ROUTES_GENERATE = "/generate";

    public static final String BY_ID = "/{id}";
    public static final String RECOMMENDATIONS = "/recommendations";

    public static final String PHOTOS = BY_ID + "/photos";
    public static final String PHOTO_BY_ID = PHOTOS + "/{photoId}";
    public static final String REVIEWS = BY_ID + "/reviews";
    public static final String REVIEW_BY_ID = REVIEWS + "/{reviewId}";

}
