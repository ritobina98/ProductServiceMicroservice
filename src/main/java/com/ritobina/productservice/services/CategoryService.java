package com.ritobina.productservice.services;

import com.ritobina.productservice.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void deleteById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
