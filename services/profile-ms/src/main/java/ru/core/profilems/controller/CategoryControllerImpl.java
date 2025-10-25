package ru.core.profilems.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.core.profilems.dto.CategoryDto;
import ru.core.profilems.mapper.CategoryMapper;
import ru.core.profilems.model.Category;
import ru.core.profilems.service.CategoryService;
import ru.core.profilems.validation.OnCreate;
import ru.core.profilems.validation.OnUpdate;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryControllerImpl {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDto> dto = categoryMapper.toDto(categories);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") UUID id) {
        Category category = categoryService.getCategory(id);
        CategoryDto dto = categoryMapper.toDto(category);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @RequestBody @Validated(OnCreate.class) CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        category = categoryService.create(category);

        return ResponseEntity
                .created(URI.create("/api/v1/category/" + category.getId()))
                .body(categoryMapper.toDto(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable("id") UUID id,
            @RequestBody @Validated(OnUpdate.class) CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        category = categoryService.update(id, category);
        return ResponseEntity.ok(categoryMapper.toDto(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") UUID id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
