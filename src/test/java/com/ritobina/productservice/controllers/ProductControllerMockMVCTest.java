package com.ritobina.productservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ritobina.productservice.models.Product;
import com.ritobina.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebMvcTest(ProductController.class)
public class ProductControllerMockMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllProductsAPI() throws Exception {

        Product p1 = new Product();
        p1.setId(1L);
        p1.setTitle("Iphone 15");
        p1.setDescription("Iphone 15");
        p1.setPrice(50000.0);

        Product p2 = new Product();
        p1.setId(2L);
        p1.setTitle("Iphone 16");
        p1.setDescription("Iphone 16");
        p1.setPrice(60000.0);

        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p2);

        when(productService.getAllProducts()).thenReturn(products);

        String expectedJson = objectMapper.writeValueAsString(products);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/")
        ) .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().json(expectedJson));
    }
}
