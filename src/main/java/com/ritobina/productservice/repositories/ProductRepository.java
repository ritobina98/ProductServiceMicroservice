package com.ritobina.productservice.repositories;

import com.ritobina.productservice.models.Category;
import com.ritobina.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Override
    Optional<Product> findById(Long productId);

    //iPhone
    //select * from products where lower(title) LIKE '%iphone%'
    List<Product> findByTitleContainsIgnoreCase(String title);

    //find all the products where price >= 100 and <= 1000
    List<Product> findByPriceBetween(Double priceAfter, Double priceBefore);

    //select * from products where category_id = category.id;
    List<Product> findByCategory(Category category);

    List<Product> findAllByCategory_Id(Long categoryId);

    //JOIN Query
    List<Product> findAllByCategory_Title(String categoryTitle);

//    @Query("select title from products where id = ?")
//    Optional<Product> findProductTitleById(Long productId);
    Product save (Product product);

    @Override
    void deleteById(Long productId);

    //@Query("select p from Product p where p.id = ?1")
    //@Query("select p.id, p.title from Product p where p.id = ?1")
    //@Query("select p.title as title, p.description as description from Product p where p.id = :id")
    @Query(value = "select p from product where p.id = :id", nativeQuery = true)
    Product findProductwithGivenId(@Param("id") Long productId);
}
