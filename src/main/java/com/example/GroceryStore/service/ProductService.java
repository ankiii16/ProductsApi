package com.example.GroceryStore.service;

import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.exception.ResourceNotFoundException;
import com.example.GroceryStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;


    public Page<Product> getAllProducts(int offset, int pageSize) {
        return productRepository.findAll(PageRequest.of(offset,pageSize));
    }

    public Optional<Page<Product>> getAllProductsByName(int offset, int pageSize, String name) {
//        return productRepository.findAll(PageRequest.of(offset,pageSize));
        Optional<List<Product>> byNameContaining = productRepository.findByNameLike(name);
        if(!byNameContaining.get().isEmpty() && byNameContaining.isPresent()){
            Pageable paging = PageRequest.of(offset, pageSize);
            int start = Math.min((int)paging.getOffset(), byNameContaining.get().size());
            int end = Math.min((start + paging.getPageSize()), byNameContaining.get().size());
            Page<Product> pd=new PageImpl<>(byNameContaining.get().subList(start,end),paging,byNameContaining.get().size());
            return Optional.of(pd);
        }
        else{
            throw new ResourceNotFoundException("No product found with following keyword");
        }

    }



}