package ru.ci_trainee.routes_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class RouteId implements Serializable {
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "place_id", nullable = false)
    private UUID placeId;
}