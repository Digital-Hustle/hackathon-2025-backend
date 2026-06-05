package rnd.sueta.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import rnd.sueta.constants.ReviewConstants;
import rnd.sueta.constants.ValidationErrorMessages;

public record CreateReviewRq(

        @NotNull(message = "rate " + ValidationErrorMessages.IS_REQUIRED)
        @Range(
                min = ReviewConstants.MIN_RATE_VALUE,
                max = ReviewConstants.MAX_RATE_VALUE,
                message = "rate " + ValidationErrorMessages.RATE_RANGE_MESSAGE)
        Integer rate,

        @NotBlank(message = "comment " + ValidationErrorMessages.IS_REQUIRED)
        @Size(
                max = ReviewConstants.COMMENT_MAX_LENGTH,
                message = "comment " + ValidationErrorMessages.STRING_IS_TOO_LONG)
        String comment
) {
}
