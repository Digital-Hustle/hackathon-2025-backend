package rnd.sueta.event_ms.validator;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rnd.sueta.event_ms.constants.ErrorMessages;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlaceValidator {

    public static void checkPlaceExistence(boolean exists) {
        if (!exists) {
            throw new EntityNotFoundException(
                    String.format(ErrorMessages.NOT_FOUND, "Place")
            );
        }
    }
}
