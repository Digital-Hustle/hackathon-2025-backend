package ru.core.profilems.mapper;

import org.mapstruct.Mapper;
import ru.core.profilems.dto.CategoryDto;
import ru.core.profilems.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);

    List<CategoryDto> toDto(List<Category> categories);
}
