package com.ritobina.productservice.controllers;

import com.ritobina.productservice.exceptions.ProductNotFoundException;
import com.ritobina.productservice.models.Product;
import com.ritobina.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {
    @MockitoBean
    private ProductService productService;

    @Autowired
    private ProductController productController;

    @Test
    public void testGetSingleProductPositiveCase() throws ProductNotFoundException {
        //Arrange
        Long productId = 10L;
        Product expectedProduct = new Product();
        expectedProduct.setId(productId);
        expectedProduct.setTitle("Iphone 16");
        expectedProduct.setPrice(70000.0);

        when(productService.getSingleProduct(productId)).thenReturn(expectedProduct);

        //Act
        Product actualProduct = productController.getSingleProduct(productId);

        //Assert
        assertEquals(expectedProduct, actualProduct);
        assertEquals(productId, actualProduct.getId());
        assertEquals("Iphone 16", actualProduct.getTitle());
        assertEquals(70000.0, actualProduct.getPrice()); // The objects are same, you need to compare if you want to check that the controller is not changing any values

    }

    @Test
    public void testGetSingleProductNegativeCase(){

    }

    @Test
    public void testGetSingleProductThrowsProductNotFountException() throws ProductNotFoundException {
        //Arrange
        ProductNotFoundException productNotFoundException = new ProductNotFoundException("Please pass correct productId");
        Long productId = -1L;
        when(productService.getSingleProduct(productId))
                .thenThrow(productNotFoundException);

        //Act & Assert
        Exception exception = assertThrows(
                ProductNotFoundException.class,
                () -> productController.getSingleProduct(productId)
        );

        assertEquals(productNotFoundException.getMessage(), exception.getMessage());
    }
}