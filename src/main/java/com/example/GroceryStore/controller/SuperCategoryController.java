package com.example.GroceryStore.controller;


import com.example.GroceryStore.Utils;
import com.example.GroceryStore.constants.Constant;
import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.entity.SubCategory;
import com.example.GroceryStore.entity.SuperCategory;
import com.example.GroceryStore.exception.ResourceNotFoundException;
import com.example.GroceryStore.response.ResponseHandler;
import com.example.GroceryStore.service.SuperCategoryService;
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
public class SuperCategoryController implements BaseController {
    int pageSize= Constant.PAGE_SIZE;

    @Autowired
    SuperCategoryService superCategoryService;


    @GetMapping("getAllSubCategoriesBySuperCategory")
    private ResponseEntity getAllSubCategoriesBySuperCategory(@RequestParam("name") String superCategoryName){
        return ResponseHandler.generateResponse("Success", HttpStatus.OK,this.superCategoryService.getAllSubCategoriesBySuperCategory(superCategoryName) ,false,0,0);
    }




    @Override
    @GetMapping("getAllSuperCategories")
    public ResponseEntity getAll(Optional<Integer> pageNumber) {
        int offset= Utils.optionalToValue(pageNumber);
        Page<SuperCategory> allSuperCategory = this.superCategoryService.findAllSuperCategory(offset,pageSize);
        return ResponseHandler.generateResponse("Success", HttpStatus.OK,allSuperCategory.getContent(),
                true,allSuperCategory.getTotalPages(),allSuperCategory.getTotalElements());
    }


    @Override
    @GetMapping("getAllProductsBySuperCategory")
    public ResponseEntity getAllProductsByName(Optional<String> name, Optional<Integer> pageNumber) {
        if(name.isPresent()){
            int offset= Utils.optionalToValue(pageNumber);
            Optional<Page<ProductDto>> productDto=this.superCategoryService.getAllProductsBySuperCategory(name.get(),offset,pageSize);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK,productDto.get().getContent() ,true,productDto.get().getTotalPages(), productDto.get().getTotalElements());
        }
        else
            throw new ResourceNotFoundException("Super category name is required");
    }
}
