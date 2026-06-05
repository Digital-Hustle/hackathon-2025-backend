package ru.ci_trainee.authms.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenConstants {

    public static final String KEY_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final String BASE_PREFIX = "dh_";
    public static final long VALIDITY_MINUTES = 15;
    public static final int BASE_LENGTH = 25;
}
