package ru.ci_trainee.routes_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "routes")
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    @EmbeddedId
    private RouteId routeId;

    @Column(name = "profile_id")
    private UUID profileId;

//    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<TopRoute> topRoutes;
}


