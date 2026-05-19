package ru.ci_trainee.authms.exception.exception;

import ru.digital_hustle.exceptions_starter.exception.DomainException;

public class EntityAlreadyExistsException extends DomainException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
