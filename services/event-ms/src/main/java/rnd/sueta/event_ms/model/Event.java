package rnd.sueta.event_ms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events", schema = "event")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "duration", columnDefinition = "INTERVAL")
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration duration;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "age_restriction")
    private Integer ageRestriction;
}
