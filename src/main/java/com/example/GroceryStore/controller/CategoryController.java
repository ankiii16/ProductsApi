package com.example.GroceryStore.controller;

import com.example.GroceryStore.Utils;
import com.example.GroceryStore.constants.Constant;
import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.exception.ResourceNotFoundException;
import com.example.GroceryStore.response.ResponseHandler;
import com.example.GroceryStore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
public class CategoryController implements BaseController{

    @Autowired
    CategoryService categoryService;
    int pageSize= Constant.PAGE_SIZE;

    @GetMapping("/getAllCategories")
    @Override
    public ResponseEntity getAll(Optional<Integer> pageNumber){
        return ResponseHandler.generateResponse("Success", HttpStatus.OK,this.categoryService.getAllCategories() ,false,0,0);
    }


    @GetMapping("/getAllSuperCategoryByCategory")
    private ResponseEntity getAllSuperCategoryByCategory(@RequestParam("name") Optional<String> categoryName){
        if(categoryName.isPresent())
        return ResponseHandler.generateResponse("Success", HttpStatus.OK,this.categoryService.getAllSuperCategoriesByCategory(categoryName.get()) ,false,0,0);
        else
            throw new ResourceNotFoundException("Category Name is Required");
    }

    @Override
    @GetMapping("/getAllProductsByCategory")
    public ResponseEntity getAllProductsByName(Optional<String> name, Optional<Integer> pageNumber) {
        if(name.isPresent()){
            int offset= Utils.optionalToValue(pageNumber);
            Optional<Page<ProductDto>> productDto=this.categoryService.getAllProductsByCategory(name.get(),offset,pageSize);
            return ResponseHandler.generateResponse("Success", HttpStatus.OK,productDto.get().getContent() ,true,productDto.get().getTotalPages(), productDto.get().getTotalElements());
        }
        else
            throw new ResourceNotFoundException("Category Name is Required");

    }
    @GetMapping("/getProductDescriptionByProductId")
    public ResponseEntity getProductDescriptionByProductId(@RequestParam("id") Optional<Long> id) {
        if(id.isPresent())
        return ResponseHandler.generateResponse("Success", HttpStatus.OK,this.categoryService.getProductDescById(id.get()),false,0,0);
        else
            throw new ResourceNotFoundException("Product id is Required");

    }

}
