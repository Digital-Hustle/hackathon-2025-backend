package ru.core.profilems.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.core.profilems.enums.CategoryType;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    @NotNull(message = "Id can't be null")
    private UUID id;

    @NotNull(message = "Category type can't be null")
    private CategoryType name;
}
