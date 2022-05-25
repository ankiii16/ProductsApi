package com.example.GroceryStore.controller;

import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.response.ResponseHandler;
import com.example.GroceryStore.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/file")
public class StorageController {

    @Autowired
    StorageService storageService;
    @PostMapping("/addProduct")
    public ResponseEntity addProduct(@RequestParam(value="file")MultipartFile file) {
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, storageService.uploadFile(file));
    }
}
