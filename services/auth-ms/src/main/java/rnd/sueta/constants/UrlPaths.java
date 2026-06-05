package rnd.sueta.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlPaths {

    public static final String API = "/api";
    public static final String API_VERSION = API + "/v{version}";

    public static final String AUTH = "/auth";

    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";

    public static final String TOKENS = "/tokens";
    public static final String TOKENS_ACCESS = TOKENS + "/access";
    public static final String TOKENS_BOTH = TOKENS + "/both";

    public static final String PASSWORD_RESET = "/password-reset";
    public static final String PASSWORD_RESET_CONFIRMATION = PASSWORD_RESET + "/confirmation";
}
