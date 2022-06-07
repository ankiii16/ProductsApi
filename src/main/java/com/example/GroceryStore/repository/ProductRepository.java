package com.example.GroceryStore.repository;

import com.example.GroceryStore.entity.Brand;
import com.example.GroceryStore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM products p where p.name LIKE '%'||:name||'%' ")
    Optional<Page<Product>> findByNameLike(String name, Pageable page);

    Optional<List<Product>> findByName(String name);


    Optional<Product> findById(long id);


}
