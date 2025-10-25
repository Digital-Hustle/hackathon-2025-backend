package ru.ci_trainee.routes_service.model.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    private Double lat; // широта
    private Double lon;

    @Override
    public String toString() {
        return String.format("Point{lat=%.6f, lon=%.6f}", lat, lon);
    }
}
