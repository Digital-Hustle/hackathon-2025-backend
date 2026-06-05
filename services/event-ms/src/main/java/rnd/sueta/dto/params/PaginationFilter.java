package rnd.sueta.dto.params;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import rnd.sueta.constants.PageConstants;
import rnd.sueta.constants.ValidationErrorMessages;

public record PaginationFilter(
        @Min(value = PageConstants.PAGE_MIN_VALUE, message = ValidationErrorMessages.NEGATIVE_PAGE_NUMBER)
        @NotNull(message = "page " + ValidationErrorMessages.IS_REQUIRED)
        Integer page,

        @Min(value = PageConstants.PAGE_SIZE_MIN_VALUE, message = ValidationErrorMessages.INVALID_PAGE_SIZE)
        @NotNull(message = "size " + ValidationErrorMessages.IS_REQUIRED)
        Integer size
) {
}
