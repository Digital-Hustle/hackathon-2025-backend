package ru.ci_trainee.routes_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "top_10_routs", schema = "profile_dev")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopRoute {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    @Column(name = "position")
    private Integer position;
}
