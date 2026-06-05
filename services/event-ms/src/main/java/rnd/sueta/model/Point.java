package rnd.sueta.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record Point(

        BigDecimal latitude,

        BigDecimal longitude
) {
}
