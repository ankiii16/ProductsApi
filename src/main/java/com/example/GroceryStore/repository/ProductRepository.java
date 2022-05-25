package com.example.GroceryStore.repository;

import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductDetailsProductQRCode(BigDecimal productQRCode);
    //
    Optional<Product> deleteByProductDetailsProductQRCode(BigDecimal productQRCode);
}
