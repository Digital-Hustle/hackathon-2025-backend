package ru.ci_trainee.routes_service.dto.searchEvents;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ci_trainee.routes_service.model.Util.Category;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchPlacesDto {

    private List<Double> rangeLat; //min - max
    private List<Double> rangeLon; //min - max
    private List<Category> categories;
}
