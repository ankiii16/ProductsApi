package com.example.GroceryStore.controller;

import com.example.GroceryStore.entity.Product;
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
@RequestMapping("/api/v1/products")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping("/getAllProducts")
    public ResponseEntity getAllProducts(@RequestParam("offset") int offset,@RequestParam("pageSize") int pageSize) {
        Page<Product> allProducts = this.productService.getAllProducts(offset, pageSize);

        return ResponseHandler.generateResponse("Success", HttpStatus.OK,allProducts.getContent(),

                true,allProducts.getTotalPages(),allProducts.getTotalElements());
    }

    @GetMapping("/getAllProductByName")
    public ResponseEntity getAllProductsByName(@RequestParam("offset") int offset,@RequestParam("pageSize") int pageSize,@RequestParam("name") String name){

       Optional< Page<Product>> allProducts = this.productService.getAllProductsByName(offset, pageSize,name);
       return ResponseHandler.generateResponse("Success",HttpStatus.OK,allProducts.get().getContent(),true,allProducts.get().getTotalPages(),allProducts.get().getTotalElements());
    }

}