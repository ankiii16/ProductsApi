package com.example.GroceryStore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public interface BaseController {


    public   ResponseEntity getAll(Optional<Integer> pageNumber);
    public  abstract ResponseEntity getAllProductsByName(Optional<String> name,Optional<Integer> pageNumber);
}
