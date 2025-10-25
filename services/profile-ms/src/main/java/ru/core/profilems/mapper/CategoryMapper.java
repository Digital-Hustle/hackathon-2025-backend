package ru.core.profilems.mapper;

import org.mapstruct.Mapper;
import ru.core.profilems.dto.CategoryDto;
import ru.core.profilems.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);
}
