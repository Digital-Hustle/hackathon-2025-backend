package rnd.sueta.event_ms.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import rnd.sueta.event_ms.annotations.constraints.UrlValidatorConstraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UrlValidatorConstraint.class)
@Documented
public @interface ValidUrl {

    String message() default "Invalid URL format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] schemes() default {"http", "https"};
}
