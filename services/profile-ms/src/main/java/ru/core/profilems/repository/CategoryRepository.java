package ru.core.profilems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.core.profilems.enums.CategoryType;
import ru.core.profilems.model.Category;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByNameIn(Collection<CategoryType> types);
}
