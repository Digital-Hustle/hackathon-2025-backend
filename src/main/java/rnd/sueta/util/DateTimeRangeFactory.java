package rnd.sueta.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rnd.sueta.model.DateTimeRange;

import java.time.OffsetDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeRangeFactory {

    public static DateTimeRange getRange(OffsetDateTime startDate, int daysAfterStart) {
        OffsetDateTime startOfDay = startDate.toLocalDate().atStartOfDay().atOffset(startDate.getOffset());
        OffsetDateTime endOfDay = startOfDay.plusDays(daysAfterStart).minusNanos(1);

        return DateTimeRange.builder()
                .start(startOfDay)
                .end(endOfDay)
                .build();
    }
}
