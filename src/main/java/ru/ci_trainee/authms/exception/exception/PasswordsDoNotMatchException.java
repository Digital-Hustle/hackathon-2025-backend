package ru.ci_trainee.authms.exception.exception;

import ru.digital_hustle.exceptions_starter.exception.DomainException;

public class PasswordsDoNotMatchException extends DomainException {
    public PasswordsDoNotMatchException(String message) {
        super(message);
    }
}
