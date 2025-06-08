package com.ritobina.productservice.controllers;

import com.ritobina.productservice.services.CategoryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private  CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long categoryId) {
        categoryService.deleteById(categoryId);
    }
}
