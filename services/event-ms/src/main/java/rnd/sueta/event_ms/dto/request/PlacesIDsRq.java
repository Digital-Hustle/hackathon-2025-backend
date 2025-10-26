package rnd.sueta.event_ms.dto.request;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record PlacesIDsRq(
        List<UUID> ids
) {
}
