package rnd.sueta.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import rnd.sueta.constants.PlaceConstants;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "points", schema = "event")
public class Point {
    @Id
    private UUID id;

    @Column(
            name = "latitude",
            precision = PlaceConstants.COORDINATE_PRECISION,
            scale = PlaceConstants.COORDINATE_SCALE,
            nullable = false)
    private BigDecimal latitude;

    @Column(
            name = "longitude",
            precision = PlaceConstants.COORDINATE_PRECISION,
            scale = PlaceConstants.COORDINATE_SCALE,
            nullable = false)
    private BigDecimal longitude;

    @ConstructorBinding
    public Point(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
