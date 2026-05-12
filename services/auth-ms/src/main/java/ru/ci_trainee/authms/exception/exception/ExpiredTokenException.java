package ru.ci_trainee.authms.exception.exception;

import ru.digital_hustle.exceptions_starter.exception.DomainException;

public class ExpiredTokenException extends DomainException {
    public ExpiredTokenException(String message) {
        super(message);
    }
}
