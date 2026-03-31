package rnd.sueta.event_ms.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import rnd.sueta.event_ms.constants.ErrorMessages;
import rnd.sueta.event_ms.exception.custom.UndefinedProfileException;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProfileValidator {

    public static void checkProfileIdIsPresent(String profileId) {
        if (StringUtils.isBlank(profileId)) {
            throw new UndefinedProfileException(ErrorMessages.PROFILE_UNDEFINED);
        }
    }

    public static void checkProfileIdIsUuid(String profileId) {
        try {
            UUID.fromString(profileId);
        } catch (IllegalArgumentException exception) {
            throw new UndefinedProfileException(ErrorMessages.INVALID_PROFILE_ID_TYPE);
        }
    }
}
