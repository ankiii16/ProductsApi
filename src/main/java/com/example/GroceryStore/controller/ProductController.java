package com.example.GroceryStore.controller;

import com.example.GroceryStore.Utils;
import com.example.GroceryStore.constants.Constant;
import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.exception.ResourceNotFoundException;
import com.example.GroceryStore.repository.ProductRepository;
import com.example.GroceryStore.response.ResponseHandler;
import com.example.GroceryStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@RestController
public class ProductController implements BaseController{


    int pageSize= Constant.PAGE_SIZE;
    @Autowired
    private ProductService productService;

    @Override
    @GetMapping("getAllProducts")
    public ResponseEntity getAll(@RequestParam("pageNumber") Optional<Integer> pageNumber) {
        int offset=Utils.optionalToValue(pageNumber);
        Page<Product> allProducts = this.productService.getAllProducts(offset, pageSize);
        return ResponseHandler.generateResponse("Success", HttpStatus.OK,allProducts.getContent(),
                true,allProducts.getTotalPages(),allProducts.getTotalElements());
    }

    @Override
    @GetMapping("getAllProductsByName")
    public ResponseEntity getAllProductsByName(Optional<String> name, Optional<Integer> pageNumber) {
        if(name.isPresent()){
            int offset=Utils.optionalToValue(pageNumber);
            Optional<Page<ProductDto>> productDto = this.productService.getAllProductsByName(offset, pageSize, name.get());
            return ResponseHandler.generateResponse("Success", HttpStatus.OK,productDto.get().getContent(),
                    true,productDto.get().getTotalPages(),productDto.get().getTotalElements());
        }
        else{
            throw new ResourceNotFoundException("product name is required");
        }

    }
}