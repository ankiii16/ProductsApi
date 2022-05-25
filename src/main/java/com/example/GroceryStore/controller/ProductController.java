package com.example.GroceryStore.controller;

import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.repository.ProductRepository;
import com.example.GroceryStore.response.ResponseHandler;
import com.example.GroceryStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getProducts")
    public ResponseEntity getAllProducts() {
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, this.productService.getAllProducts());
    }

    @GetMapping("/getProductByCode")
    public ResponseEntity getProductByCode(@RequestParam BigDecimal productQRCode) {
        return ResponseHandler.generateResponse("Product Found", HttpStatus.OK, productService.getProductByQRCode(productQRCode));
    }


    @PutMapping("/updateProduct")
    public ResponseEntity updateProductByCode(@RequestParam BigDecimal productQRCode, @Valid @RequestBody Product product) {
        return ResponseHandler.generateResponse("Product Found", HttpStatus.OK, productService.updateProduct(productQRCode, product.getProductDetails().getProductPrice()));
    }

    @PostMapping("/addProduct")
    public ResponseEntity addProduct(@Valid @RequestBody Product product) {
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, productService.addProduct(product));
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity deleteProduct(@RequestParam BigDecimal productQRCode) {
        return ResponseHandler.generateResponse("Product Successfully Deleted!", HttpStatus.OK, productService.deleteProduct(productQRCode));
    }
}