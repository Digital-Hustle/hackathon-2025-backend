package rnd.sueta.event_ms.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rnd.sueta.event_ms.constants.PlaceConstants;
import rnd.sueta.event_ms.enums.PlaceType;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "places", schema = "event")
public class Place {

    @Id
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "address", nullable = false, length = PlaceConstants.MAX_ADDRESS_LENGTH)
    private String address;

    @Column(name = "type", nullable = false)
    private PlaceType type;

    @Column(name = "total_rating", precision = PlaceConstants.RATING_PRECISION, scale = PlaceConstants.RATING_SCALE)
    private BigDecimal totalRating;

    @Column(name = "reviews_amount", precision = PlaceConstants.RATING_PRECISION, scale = PlaceConstants.RATING_SCALE)
    private Integer reviews_amount;

    @Column(name = "total_visits")
    private Integer totalVisits;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "point_id", nullable = false)
    private UUID pointId;

    @Column(name = "recommended", nullable = false)
    private Boolean recommended;

    @Column(name = "contacts")
    private Contacts contacts;
}
