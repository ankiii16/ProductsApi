package com.example.GroceryStore.controller;

import com.example.GroceryStore.Utils;
import com.example.GroceryStore.constants.Constant;
import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.entity.SubCategory;
import com.example.GroceryStore.exception.ResourceNotFoundException;
import com.example.GroceryStore.response.ResponseHandler;
import com.example.GroceryStore.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class SubCategoryController implements BaseController {

    @Autowired
    SubCategoryService subCategoryService;
    int pageSize= Constant.PAGE_SIZE;

    @Override
    @GetMapping("getAllSubCategories")
    public ResponseEntity getAll(@RequestParam("pageNumber") Optional<Integer> pageNumber) {

        int offset= Utils.optionalToValue(pageNumber);
        Page<SubCategory> allSubCategories = this.subCategoryService.getAllSubCategory(offset, pageSize);
        return ResponseHandler.generateResponse("Success", HttpStatus.OK,allSubCategories.getContent(),
                true,allSubCategories.getTotalPages(),allSubCategories.getTotalElements());
    }

    @Override
    @GetMapping("getAllProductsBySubCategory")
    public ResponseEntity getAllProductsByName(@RequestParam("name") Optional<String> subCategoryName,@RequestParam("pageNumber") Optional<Integer> pageNumber) {
        if(subCategoryName.isPresent()){
            int offset= Utils.optionalToValue(pageNumber);
            Optional<Page<ProductDto>> productDto  = this.subCategoryService.getAllProductsBySubCategory(subCategoryName.get(),offset, pageSize);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK,productDto.get().getContent(),
                    true,productDto.get().getTotalPages(),productDto.get().getTotalElements());
        }
        else{
            throw new ResourceNotFoundException("Sub category name is required");
        }
    }
}
