package rnd.sueta.event_ms.annotations.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import rnd.sueta.event_ms.annotations.ValidUrl;

public class UrlValidatorConstraint implements ConstraintValidator<ValidUrl, String> {

    private UrlValidator urlValidator;

    @Override
    public void initialize(ValidUrl annotation) {
        this.urlValidator = new UrlValidator(annotation.schemes());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        return urlValidator.isValid(value);
    }
}
