package rnd.sueta.event_ms.model;

import lombok.Builder;
import rnd.sueta.event_ms.enums.PlaceType;
import rnd.sueta.event_ms.model.entity.Contacts;

import java.math.BigDecimal;
import java.util.UUID;

@Builder(toBuilder = true)
public record PlaceWithCoordinates(

        UUID id,

        String title,

        String address,

        PlaceType type,

        Integer totalRating,

        Integer reviewsAmount,

        Double averageRating,

        Integer totalVisits,

        UUID ownerId,

        UUID pointId,

        BigDecimal latitude,

        BigDecimal longitude,

        Contacts contacts
) {
}
