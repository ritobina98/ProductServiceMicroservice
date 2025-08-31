package com.ritobina.productservice.services;

import com.ritobina.productservice.dtos.FakeStoreProductDto;
import com.ritobina.productservice.exceptions.ProductNotFoundException;
import com.ritobina.productservice.models.Category;
import com.ritobina.productservice.models.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
//@Primary
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;
    private RedisTemplate<String,Object> redisTemplate;

    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate<String,Object> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }
    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        //throw new RuntimeException("Not implemented yet");

        //First check if the product with this id exists in Redis
        //If exists return from Redis
        Product product = (Product) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCT_" + productId);
        if(product != null) {
            //CACHE HIT
            return product;
        }
        //CACHE MISS
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                restTemplate.getForEntity("https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class);

        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();
        if(fakeStoreProductDto == null){
            throw new ProductNotFoundException(productId, "Product with id " + productId + " does not exist");
        }
        product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);

        //Before returning save the product to Redis
        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_" + productId, product);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtoResponse =
                restTemplate.getForEntity("https://fakestoreapi.com/products",
                        FakeStoreProductDto[].class
                );
        FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreProductDtoResponse.getBody();
        List<Product> products = new ArrayList<>();

        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos){
            Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
            products.add(product);
        }
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public Page<Product> getProductsByTitle(String title, int pageNumber, int pageSize) {
        return Page.empty();
    }

    private static Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImgUrl(fakeStoreProductDto.getImage());

        Category category = new Category();
        category.setTitle(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
