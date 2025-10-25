package rnd.sueta.event_ms.dto.request;

import jakarta.validation.constraints.NotNull;
import rnd.sueta.event_ms.enums.Category;

import java.math.BigDecimal;
import java.util.List;

public record SearchEventParamsRq(
        @NotNull
        List<BigDecimal> rangeLat,
        @NotNull
        List<BigDecimal> rangeLon,
        @NotNull
        List<Category> categories
) {
}
