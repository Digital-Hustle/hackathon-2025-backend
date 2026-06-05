package rnd.sueta.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record ItemWithScore<T>(

        UUID itemId,

        Double score,

        T item,

        Class<T> type
) {
}
