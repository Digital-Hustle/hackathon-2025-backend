package ru.ci_trainee.authms.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourceLocations {

    public static final String TEMPLATES_ROOT = "classpath:templates";

    public static final String EMAIL_TEMPLATES = TEMPLATES_ROOT + "/email";

    public static final String RESSET_PASSWORD_TEMPLATE = EMAIL_TEMPLATES + "/reset-password.html";
}
