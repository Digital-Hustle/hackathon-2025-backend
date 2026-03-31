package rnd.sueta.event_ms.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rnd.sueta.event_ms.constants.EventConstants;
import rnd.sueta.event_ms.enums.EventType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "events", schema = "event")
public class Event {

    @Id
    private UUID id;

    @Column(name = "organizer_id", nullable = false)
    private UUID organizerId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "type", nullable = false)
    private EventType type;

    @Column(name = "rating", precision = EventConstants.RATING_PRECISION, scale = EventConstants.RATING_SCALE)
    private BigDecimal rating;

    @Column(name = "event_start", nullable = false)
    private OffsetDateTime eventStart;

    @Column(name = "event_end", nullable = false)
    private OffsetDateTime eventEnd;

    @Column(name = "price", precision = EventConstants.PRICE_PRECISION, scale = EventConstants.PRICE_SCALE)
    private BigDecimal price;

    @Column(name = "age_restriction")
    private Integer ageRestriction;

    @Column(name = "place_id", nullable = false)
    private UUID placeId;

    @Column(name = "recommended", nullable = false)
    private Boolean recommended;
}
