package ru.core.profilems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.core.profilems.model.Category;
import ru.core.profilems.repository.CategoryRepository;
import ru.core.profilems.repository.ProfileRepository;
import ru.core.profilems.exception.exception.CategoryNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProfileRepository profileRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Transactional
    public Category create(Category category){
        if (category.getId() == null) {
            throw new IllegalArgumentException("Category ID must be provided");
        }
        if(categoryRepository.existsById(category.getId()))
            throw new IllegalArgumentException("Category with ID " + category.getId() + " already exists");

        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(UUID id, Category categoryDetails) {
        if (!id.equals(categoryDetails.getId())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }

        Category existing = getCategory(id);
        existing.setName(categoryDetails.getName());
        return categoryRepository.save(existing);
    }

    @Transactional
    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }
}
