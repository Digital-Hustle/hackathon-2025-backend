package ru.core.profilems.exception.exception;

// TODO вот тут во всех исключениях наследоваться от DomainException
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("Access denied.");
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
