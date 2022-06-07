package com.example.GroceryStore.repository;

import com.example.GroceryStore.entity.SuperCategory;
import org.hibernate.mapping.Any;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SuperCategoryRepository extends JpaRepository<SuperCategory,Long> {

    Optional<List<SuperCategory>> findByName(String name);
}
