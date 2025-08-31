package com.ritobina.productservice.controllers;

import com.ritobina.productservice.dtos.ExceptionDto;
import com.ritobina.productservice.exceptions.CategoryNotFoundException;
import com.ritobina.productservice.exceptions.ProductNotFoundException;
import com.ritobina.productservice.models.Product;
import com.ritobina.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(/*@Qualifier("selfProductService") */ProductService productService){
        this.productService = productService;
    }
    @GetMapping("/{id}")
    public Product getSingleProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        /*ResponseEntity<Product> responseEntity = new ResponseEntity<>(
                productService.getSingleProduct(productId), HttpStatus.OK
        );*/

        Product product = productService.getSingleProduct(productId);
        product.setPrice(80000.0);
        return product;
        /*Product product = null;
         try{
             product = productService.getSingleProduct(productId);
              responseEntity = new ResponseEntity<>(product, HttpStatus.OK);
         }catch (RuntimeException e){
             e.printStackTrace();
             responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }*/
        //return responseEntity;
    }
    @GetMapping("/")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/title/{title}/{pageNumber}/{pageSize}")
    public Page<Product> getProductsByTitle(@PathVariable("title") String title, @PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize){
        return productService.getProductsByTitle(title, pageNumber, pageSize);
    }
    @PostMapping("/")
    public Product createProduct(@RequestBody Product product) throws CategoryNotFoundException {
        return productService.createProduct(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long productId){
        productService.deleteProduct(productId);
        return null;
    }

    /*@PutMapping("/")
    public Product updateProduct(@RequestBody Product product){
        return product;
    }

    @PatchMapping("/{id}")
    public Product updateProductPartially(@PathVariable("id") Long productId, @RequestBody Product product){
        return product;
    }*/

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> HandleRuntimeExceptions(){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage("Handling exception from Controller");
        return new ResponseEntity<>(exceptionDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
