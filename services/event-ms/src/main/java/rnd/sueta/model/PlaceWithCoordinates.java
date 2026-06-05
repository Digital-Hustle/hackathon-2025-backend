package rnd.sueta.model;

import lombok.Builder;
import rnd.sueta.enums.PlaceType;
import rnd.sueta.model.entity.Contacts;

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

        Boolean recommended,

        UUID ownerId,

        UUID pointId,

        BigDecimal latitude,

        BigDecimal longitude,

        Contacts contacts
) {
}
