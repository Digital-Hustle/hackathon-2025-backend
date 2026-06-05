package rnd.sueta.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "routes", schema = "event")
public class Route {
    @Id
    private UUID id;

    @Column(name = "profile_id")
    private UUID profileId;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}
