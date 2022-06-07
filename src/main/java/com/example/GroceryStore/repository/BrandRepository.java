package com.example.GroceryStore.repository;

import com.example.GroceryStore.entity.Brand;
import com.example.GroceryStore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Long> {

    Optional<Page<Brand>> findByName(String name, Pageable page);

}
