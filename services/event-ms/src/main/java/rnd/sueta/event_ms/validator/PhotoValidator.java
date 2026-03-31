package rnd.sueta.event_ms.validator;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import rnd.sueta.event_ms.constants.ErrorMessages;
import rnd.sueta.event_ms.constants.PhotoErrorMessages;
import rnd.sueta.event_ms.enums.PhotoExtension;
import rnd.sueta.event_ms.exception.custom.InvalidPhotoExtension;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PhotoValidator {

    public static void checkPhotoExtension(MultipartFile photo) {
        String extension = FilenameUtils.getExtension(photo.getOriginalFilename());

        if (extension == null) {
            throw new InvalidPhotoExtension(PhotoErrorMessages.INVALID_PHOTO_EXTENSION);
        }

        try {
            PhotoExtension.valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidPhotoExtension(PhotoErrorMessages.INVALID_PHOTO_EXTENSION);
        }
    }

    public static void checkOwnerExistence(boolean exists) {
        if (!exists) {
            throw new EntityNotFoundException(ErrorMessages.NOT_FOUND.formatted("Owner"));
        }
    }
}
