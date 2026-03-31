package rnd.sueta.event_ms.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {

    public static final String ERROR_MESSAGES_SEPARATOR = "; ";
    public static final String VALIDATION_FAILED = "Validation failed";

    public static final String NOT_FOUND = "%s not found";
    public static final String SERVICE_TEMPORARILY_UNAVAILABLE = "%s service temporarily unavailable";

    public static final String FAILED_TO_CREATE_BUCKET = "MinIO bucket initialization failed";

    public static final String PROFILE_UNDEFINED = "Profile undefined";
    public static final String INVALID_PROFILE_ID_TYPE = "Invalid profile id type. UUID expected";
}
