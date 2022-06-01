package com.example.GroceryStore.controller;

import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.response.ResponseHandler;
import com.example.GroceryStore.service.ProductDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductDescriptionController {

    @Autowired
    ProductDescriptionService productDescriptionService;

    @GetMapping("/getDescriptionByProductId")
    public ResponseEntity getProductDescriptionByProductId(@RequestParam("id") long id) {
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, this.productDescriptionService.getProductDescriptionByProductId(id),false,0,0);
    }
    @GetMapping("/getProductByPropertyName")
    public ResponseEntity getProductByPropertyName(@RequestParam("propertyName") String propertyName,@RequestParam("value") String value ,@RequestParam ("offset") int offset,@RequestParam("pageSize") int pageSize) {


        Optional<Page<ProductDto>> productByPropertyName = this.productDescriptionService.getProductByPropertyName(propertyName, value, offset, pageSize);
        return ResponseHandler.generateResponse("Success", HttpStatus.OK,productByPropertyName.get().getContent(),true,productByPropertyName.get().getTotalPages(),productByPropertyName.get().getTotalElements());
    }
}
