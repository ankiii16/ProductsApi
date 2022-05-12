package com.example.GroceryStore.repository;


import com.example.GroceryStore.entity.ProductCategory;
import com.example.GroceryStore.entity.ProductSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSubCategoryRepository extends JpaRepository<ProductSubCategory, Long> {

}
