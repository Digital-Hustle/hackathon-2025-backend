package rnd.sueta.event_ms.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import rnd.sueta.event_ms.annotations.constraints.PhonesValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhonesValidator.class)
@Documented
public @interface ValidPhones {

    String message() default "Invalid phone number(s)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String defaultRegion() default "RU";
}
