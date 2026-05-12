package ru.ci_trainee.authms.exception.exception;

import ru.digital_hustle.exceptions_starter.exception.DomainException;

public class InvalidTokenException extends DomainException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
