package com.example.GroceryStore.repository;

import com.example.GroceryStore.entity.ProductDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription,Long> {


    Optional<ProductDescription> findByProductId(long id);
    Optional<List<ProductDescription>> findByBrandName(String name);
    Optional<List<ProductDescription>> findByCategoryName(String name);
    Optional<List<ProductDescription>> findBySubCategoryName(String name);
    Optional<List<ProductDescription>> findBySuperCategoryName(String name);

}
