package ru.ci_trainee.authms.exception.exception;

import ru.digital_hustle.exceptions_starter.exception.DomainException;

public class TokenAlreadyUsedException extends DomainException {
    public TokenAlreadyUsedException(String message) {
        super(message);
    }
}
