package com.example.GroceryStore.service;

import com.example.GroceryStore.Utils;
import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.entity.SubCategory;
import com.example.GroceryStore.exception.ResourceNotFoundException;
import com.example.GroceryStore.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public Page<SubCategory> getAllSubCategory( int offset, int pageSize){
        return this.subCategoryRepository.findAll(PageRequest.of(offset,pageSize));
    }
    public Optional<Page<ProductDto>> getAllProductsBySubCategory(String subCategoryName, int offset, int pageSize){
        Optional<List<SubCategory>> subCategoryByName = this.subCategoryRepository.findByName(subCategoryName);

        if(subCategoryByName.isPresent()){
            Pageable paging = PageRequest.of(offset, pageSize);
            List<Product> products = subCategoryByName.get().get(0).getProducts();
            List<ProductDto> returningList=new ArrayList<>();
            for (Product product:products
            ) {
                returningList.add(Utils.convertProductToProductDto(product)) ;
            }
            return Optional.of(Utils.getPageFromList(paging,returningList));
        }
        else{
            throw new ResourceNotFoundException("Sub Category Not Found");
        }


    }
}
