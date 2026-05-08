package rnd.sueta.event_ms.model;

import lombok.Builder;

import java.time.Duration;

@Builder(toBuilder = true)
public record CacheProperties(

        String setKey,

        String keyPrefix,

        Duration ttl
) {
}
