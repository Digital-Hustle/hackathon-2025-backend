package ru.ci_trainee.authms.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationConstants {

    public static final String IS_REQUIRED = "is required";
    public static final String CHARACTERS_RANGE = "value should be in range between {min} and {max}";
}
