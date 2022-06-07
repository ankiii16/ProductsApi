package com.example.GroceryStore.repository;

import com.example.GroceryStore.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    Optional<List<SubCategory>> findByName(String name);
}
