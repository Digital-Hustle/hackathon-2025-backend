package ru.ci_trainee.routes_service.dto.searchPlaces;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SearchPlacesByIds {
    List<UUID> ids;
}
