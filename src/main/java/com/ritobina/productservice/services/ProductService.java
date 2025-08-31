package com.ritobina.productservice.services;

import com.ritobina.productservice.exceptions.CategoryNotFoundException;
import com.ritobina.productservice.exceptions.ProductNotFoundException;
import com.ritobina.productservice.models.Product;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    Product getSingleProduct(Long productId) throws ProductNotFoundException;
    List<Product> getAllProducts();
    Product createProduct(Product product) throws CategoryNotFoundException;
    void deleteProduct(Long productId);
    Page<Product> getProductsByTitle(String title, int pageNumber, int pageSize);
}
