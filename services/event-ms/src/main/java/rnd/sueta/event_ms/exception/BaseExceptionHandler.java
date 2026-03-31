package rnd.sueta.event_ms.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import rnd.sueta.event_ms.constants.ErrorMessages;
import rnd.sueta.event_ms.dto.response.ExceptionRs;
import rnd.sueta.event_ms.util.ExceptionResponseFactory;

import java.time.Clock;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class BaseExceptionHandler {

    private final Clock clock;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRs handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        Map<String, String> errors = fieldErrors.stream()
                .filter(fieldError -> fieldError.getDefaultMessage() != null)
                .collect(
                        Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                                (msg1, msg2) -> msg1 + ErrorMessages.ERROR_MESSAGES_SEPARATOR + msg2
                        )
                );

        return ExceptionResponseFactory.newBadRequest(ErrorMessages.VALIDATION_FAILED, clock, errors);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRs handleConstraintViolationException(HandlerMethodValidationException exception) {
        Map<String, String> errors = exception.getParameterValidationResults().stream()
                .collect(Collectors.toMap(
                        result -> result.getMethodParameter().getParameterName(),
                        result -> result.getResolvableErrors().stream()
                                .map(MessageSourceResolvable::getDefaultMessage)
                                .filter(Objects::nonNull)
                                .collect(Collectors.joining(ErrorMessages.ERROR_MESSAGES_SEPARATOR))
                ))
                .entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return ExceptionResponseFactory.newBadRequest(ErrorMessages.VALIDATION_FAILED, clock, errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRs handleHttpMessageNotReadableException() {
        return ExceptionResponseFactory.newBadRequest(ErrorMessages.VALIDATION_FAILED + " Invalid value.", clock);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRs handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return ExceptionResponseFactory.newBadRequest(exception.getMessage(), clock);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionRs handleEntityNotFoundException(EntityNotFoundException exception) {
        return ExceptionResponseFactory.newNotFound(exception.getMessage(), clock);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionRs handleException(Exception exception) {
        exception.printStackTrace();
        return ExceptionResponseFactory.newInternalServerError(clock);
    }
}
