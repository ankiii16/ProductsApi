package com.example.GroceryStore.config;


import com.example.GroceryStore.entity.ProductCategory;
import com.example.GroceryStore.entity.ProductSubCategory;
import com.example.GroceryStore.repository.ProductSubCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductSubCategoryConfig {
    @Bean
    CommandLineRunner commandLineRunner(ProductSubCategoryRepository productSubCategoryRepository) {
        return args -> {
            ProductCategory productCategory = new ProductCategory("Herbs and sweets");
            ProductSubCategory productSubCategory=new ProductSubCategory("abcd",productCategory);
            productSubCategoryRepository.save(productSubCategory);
        };
    }

}
