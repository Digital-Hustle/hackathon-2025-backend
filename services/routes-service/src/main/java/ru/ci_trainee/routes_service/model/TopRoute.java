package ru.ci_trainee.routes_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "top_10_routes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopRoute {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "route_id")
    private UUID routeId;

    @Column(name = "position")
    private Integer position;
}
