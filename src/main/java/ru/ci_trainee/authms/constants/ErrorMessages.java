package ru.ci_trainee.authms.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {

    public static final String IS_REQUIRED = "is required";

    public static final String TOKEN_USED = "Token has already been used";
    public static final String TOKEN_EXPIRED = "Token is expired";
}
