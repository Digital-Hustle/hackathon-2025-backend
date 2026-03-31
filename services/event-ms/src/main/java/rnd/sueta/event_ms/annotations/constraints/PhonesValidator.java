package rnd.sueta.event_ms.annotations.constraints;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.collections.CollectionUtils;
import rnd.sueta.event_ms.annotations.ValidPhones;

import java.util.List;

public class PhonesValidator implements ConstraintValidator<ValidPhones, List<String>> {

    private String defaultRegion;
    private PhoneNumberUtil phoneUtil;

    @Override
    public void initialize(ValidPhones annotation) {
        this.defaultRegion = annotation.defaultRegion();
        this.phoneUtil = PhoneNumberUtil.getInstance();
    }

    @Override
    public boolean isValid(List<String> phoneList, ConstraintValidatorContext context) {
        if (CollectionUtils.isEmpty(phoneList)) {
            return true;
        }

        return allNumbersAreValid(phoneList);
    }

    private boolean allNumbersAreValid(List<String> phoneList) {
        for (String phone : phoneList) {
            try {
                Phonenumber.PhoneNumber number = phoneUtil.parse(phone, defaultRegion);
                if (!phoneUtil.isValidNumber(number)) {
                    return false;
                }
            } catch (NumberParseException | NumberFormatException exception) {
                return false;
            }
        }

        return true;
    }
}
