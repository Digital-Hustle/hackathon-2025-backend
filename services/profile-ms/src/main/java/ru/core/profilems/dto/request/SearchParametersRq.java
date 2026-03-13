package ru.core.profilems.dto.request;

import lombok.Builder;

@Builder
public record SearchParametersRq(
        String query,

        boolean ignoreCase,

        Integer page,

        Integer size
) {
}
