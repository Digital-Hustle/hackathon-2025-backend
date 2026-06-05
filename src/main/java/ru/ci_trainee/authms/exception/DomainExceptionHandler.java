package ru.ci_trainee.authms.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.ci_trainee.authms.exception.exception.EntityAlreadyExistsException;
import ru.ci_trainee.authms.exception.exception.ExpiredTokenException;
import ru.ci_trainee.authms.exception.exception.InvalidTokenException;
import ru.ci_trainee.authms.exception.exception.MailSendingException;
import ru.ci_trainee.authms.exception.exception.PasswordsDoNotMatchException;
import ru.ci_trainee.authms.exception.exception.ResourceLoadingException;
import ru.ci_trainee.authms.exception.exception.TokenAlreadyUsedException;
import ru.digital_hustle.exceptions_starter.constant.ErrorMessages;
import ru.digital_hustle.exceptions_starter.constant.ExceptionConstants;
import ru.digital_hustle.exceptions_starter.dto.response.ExceptionRs;
import ru.digital_hustle.exceptions_starter.factory.ExceptionResponseFactory;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DomainExceptionHandler {

    private final ExceptionResponseFactory exceptionResponseFactory;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExpiredTokenException.class)
    public ExceptionRs handleExpiredTokenException(ExpiredTokenException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage());
        return exceptionResponseFactory.newBadRequest(ErrorMessages.VALIDATION_FAILED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTokenException.class)
    public ExceptionRs handleInvalidTokenException(InvalidTokenException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage());
        return exceptionResponseFactory.newBadRequest(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordsDoNotMatchException.class)
    public ExceptionRs handlePasswordsDoNotMatchException(PasswordsDoNotMatchException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage());
        return exceptionResponseFactory.newBadRequest(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TokenAlreadyUsedException.class)
    public ExceptionRs handleTokenAlreadyUsedException(TokenAlreadyUsedException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage());
        return exceptionResponseFactory.newBadRequest(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ExceptionRs handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage());
        return exceptionResponseFactory.newConflict(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MailSendingException.class)
    public ExceptionRs handleMailSendingException(MailSendingException exception) {
        log.error(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return exceptionResponseFactory.newInternalServerError();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResourceLoadingException.class)
    public ExceptionRs handleResourceLoadingException(ResourceLoadingException exception) {
        log.warn(ExceptionConstants.LOG_MESSAGE, exception.getMessage(), exception);
        return exceptionResponseFactory.newInternalServerError();
    }
}
