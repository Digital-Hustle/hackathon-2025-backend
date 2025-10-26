package ru.ci_trainee.routes_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ci_trainee.routes_service.model.Route;

import java.util.List;
import java.util.UUID;

@Repository
public interface RouteRepository extends JpaRepository<Route, UUID> {
    List<Route> findByRouteId_Id(UUID id);

    @Query("SELECT DISTINCT r.routeId.id FROM Route r WHERE r.profileId = :profileId")
    Page<UUID> findDistinctRouteIdsByProfileId(@Param("profileId") UUID profileId, Pageable pageable);
}