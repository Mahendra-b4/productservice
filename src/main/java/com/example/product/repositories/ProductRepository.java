package com.example.product.repositories;

import com.example.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    List<Product> findByNameAndDescriptionAndPriceGreaterThan(String name, String description, int price);

    List<Product> findTop10DistinctProductsByName(String name);

    @Query("select p from Product p where p.id = :id")
    Product getProductById(@Param("id") Long id);

    @Query("select p.id as id, p.name as title, p.price as mrp from Product p where p.id = :id")
    ProductWithSpecificDetails getSpecificDetails(@Param("id") Long id);
}
