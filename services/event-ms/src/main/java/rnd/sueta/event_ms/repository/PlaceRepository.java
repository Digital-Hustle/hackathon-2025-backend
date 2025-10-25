package rnd.sueta.event_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rnd.sueta.event_ms.enums.Category;
import rnd.sueta.event_ms.model.Place;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID> {
    @Query("SELECT p FROM Place p WHERE " +
            "p.latitude BETWEEN :minLatitude AND :maxLatitude AND " +
            "p.longitude BETWEEN :minLongitude AND :maxLongitude")
    List<Place> findPlacesByFilters(
            List<Category> categories,
            BigDecimal minLatitude,
            BigDecimal maxLatitude,
            BigDecimal minLongitude,
            BigDecimal maxLongitude
    );
}
