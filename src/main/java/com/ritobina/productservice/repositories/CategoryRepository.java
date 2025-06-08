package com.ritobina.productservice.repositories;

import com.ritobina.productservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);

    @Override
    void deleteById(Long categoryId);
}
