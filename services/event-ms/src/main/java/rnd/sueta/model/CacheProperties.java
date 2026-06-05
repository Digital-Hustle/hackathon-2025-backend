package rnd.sueta.model;

import lombok.Builder;

import java.time.Duration;

@Builder(toBuilder = true)
public record CacheProperties(

        String setKey,

        String keyPrefix,

        Duration ttl
) {
}
