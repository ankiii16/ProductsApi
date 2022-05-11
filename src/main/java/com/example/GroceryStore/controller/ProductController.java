package com.example.GroceryStore.controller;

import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.repository.ProductRepository;
import com.example.GroceryStore.response.ResponseHandler;
import com.example.GroceryStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping("/getProducts")
    public ResponseEntity getAllProducts() {
        try {
            return ResponseHandler.generateResponse("Product Successfully Fetched!", HttpStatus.OK, this.productService.getAllProducts());
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);

        }
    }

    @GetMapping("/getProductByCode")
    public ResponseEntity getProductByCode(@RequestParam long productQRCode) {
        try {
            return ResponseHandler.generateResponse("Product Found", HttpStatus.OK, productService.getProductByQRCode(productQRCode));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }


    @PutMapping("/updateProduct")
    public ResponseEntity updateProductByCode(@RequestParam Long productQRCode, @RequestBody Product product) {
        try {

            return ResponseHandler.generateResponse("Product Found", HttpStatus.OK, productService.updateProduct(productQRCode, product.getProductPrice()));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }

    }

    @PostMapping("/addProduct")
    public ResponseEntity addProduct(@RequestBody Product product) {
        try {
            productService.addProduct(product);
            return ResponseHandler.generateResponse("Product Successfully added", HttpStatus.OK, product);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.CONFLICT, null);
        }

    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity deleteProduct(@RequestParam Long productQRCode) {
        try {

            return ResponseHandler.generateResponse("Product Successfully Deleted!", HttpStatus.OK, productService.deleteProduct(productQRCode));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.CONFLICT, null);

        }
    }
}
