package rnd.sueta.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rnd.sueta.constants.ErrorMessages;
import rnd.sueta.dto.response.ExceptionRs;
import rnd.sueta.exception.custom.BucketCreationException;
import rnd.sueta.exception.custom.InvalidPhotoExtension;
import rnd.sueta.exception.custom.InvalidPhotoOwnerType;
import rnd.sueta.exception.custom.OutOfCityPointException;
import rnd.sueta.exception.custom.OwnerNotExists;
import rnd.sueta.exception.custom.PhotoProcessingException;
import rnd.sueta.exception.custom.UndefinedProfileException;
import rnd.sueta.util.ExceptionResponseFactory;

import java.time.Clock;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class DomainExceptionHandler {

    private final Clock clock;

    @ExceptionHandler(OutOfCityPointException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRs handleOutOfCityPointException(OutOfCityPointException exception) {
        return ExceptionResponseFactory.newBadRequest(exception.getMessage(), clock);
    }

    @ExceptionHandler(InvalidPhotoExtension.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRs handleInvalidPhotoExtension(InvalidPhotoExtension exception) {
        return ExceptionResponseFactory.newBadRequest(exception.getMessage(), clock);
    }

    @ExceptionHandler(InvalidPhotoOwnerType.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRs handleInvalidPhotoOwnerType(InvalidPhotoOwnerType exception) {
        return ExceptionResponseFactory.newBadRequest(exception.getMessage(), clock);
    }

    @ExceptionHandler(OwnerNotExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRs handleOwnerNotExists(OwnerNotExists exception) {
        return ExceptionResponseFactory.newBadRequest(exception.getMessage(), clock);
    }

    @ExceptionHandler(UndefinedProfileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRs handleUndefinedProfileException(UndefinedProfileException exception) {
        return ExceptionResponseFactory.newBadRequest(exception.getMessage(), clock);
    }

    @ExceptionHandler(PhotoProcessingException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ExceptionRs handlePhotoProcessingException() {
        return ExceptionResponseFactory.newServiceUnavailable(
                ErrorMessages.SERVICE_TEMPORARILY_UNAVAILABLE.formatted("Photo"), clock
        );
    }

    @ExceptionHandler(BucketCreationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionRs handleBucketCreationException() {
        return ExceptionResponseFactory.newInternalServerError(clock);
    }
}
