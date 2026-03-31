package rnd.sueta.event_ms.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record PhotoWithUrl(

        UUID id,

        String originalFileName,

        String url
) {
}
