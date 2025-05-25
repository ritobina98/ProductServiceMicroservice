package com.ritobina.productservice.services;

import com.ritobina.productservice.exceptions.CategoryNotFoundException;
import com.ritobina.productservice.exceptions.ProductNotFoundException;
import com.ritobina.productservice.models.Category;
import com.ritobina.productservice.models.Product;
import com.ritobina.productservice.repositories.CategoryRepository;
import com.ritobina.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId).orElseThrow(()->
                new ProductNotFoundException(productId,"Product with id " + productId + " does not exist"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) throws CategoryNotFoundException {
        Category category = product.getCategory();
        if(category == null){
            throw new CategoryNotFoundException("Cannot create product without category information, please try again with category");
        }
        Optional<Category> optionalCategory = categoryRepository.findByTitle(category.getTitle());
        if(optionalCategory.isEmpty()){
            //There's no category with this title
            //So we need to create a new category
            //We should not call different repository in this service layer
            category = categoryRepository.save(category);
        }
        else{
            category = optionalCategory.get();
        }
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);

    }
}
