package rnd.sueta.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record SearchFilter(

        String query,

        boolean ignoreCase,

        Integer page,

        Integer size
) {
}
