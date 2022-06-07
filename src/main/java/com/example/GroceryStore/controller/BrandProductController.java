package com.example.GroceryStore.controller;

import com.example.GroceryStore.Utils;
import com.example.GroceryStore.constants.Constant;
import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.entity.Brand;
import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.repository.BrandRepository;
import com.example.GroceryStore.response.ResponseHandler;
import com.example.GroceryStore.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class BrandProductController {

    @Autowired
    BrandService brandService;

    int pageSize= Constant.PAGE_SIZE;

    @GetMapping("getAllBrands")
    public ResponseEntity getAllBrands(@RequestParam("pageNumber") Optional<Integer> pageNumber){
        int offset= Utils.optionalToValue(pageNumber);
        Page<Brand> allBrands = this.brandService.getAllBrands(offset, pageSize);
        return ResponseHandler.generateResponse("Success", HttpStatus.OK,allBrands.getContent(),true,allBrands.getTotalPages(),allBrands.getTotalElements());
    }

    @GetMapping("getAllProductsByBrand")
    private ResponseEntity getAllProductsByBrand(@RequestParam("name") String categoryName,@RequestParam("pageNumber") Optional<Integer> pageNumber){
        int offset=Utils.optionalToValue(pageNumber);
        Optional<Page<ProductDto>> productDto=this.brandService.getAllProductsByBrand(categoryName,offset,pageSize);
        return ResponseHandler.generateResponse("Success", HttpStatus.OK,productDto.get().getContent() ,true,productDto.get().getTotalPages(), productDto.get().getTotalElements());
    }

}
