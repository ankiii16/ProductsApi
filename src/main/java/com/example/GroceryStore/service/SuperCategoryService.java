package com.example.GroceryStore.service;

import com.example.GroceryStore.Utils;
import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.dto.SubCategoryDto;
import com.example.GroceryStore.entity.Category;
import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.entity.SubCategory;
import com.example.GroceryStore.entity.SuperCategory;
import com.example.GroceryStore.exception.ResourceNotFoundException;
import com.example.GroceryStore.repository.SuperCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SuperCategoryService {


    @Autowired
    private SuperCategoryRepository superCategoryRepository;

    public Optional<Set<SubCategoryDto>> getAllSubCategoriesBySuperCategory(String superCategoryName){
        String messageIfNotFound="No sub categories found with given super category";
        SuperCategory superCategoryByName = getSuperCategoryByName(superCategoryName,messageIfNotFound);
        Set<SubCategory> subCategories = superCategoryByName.getSubCategories();
        Set<SubCategoryDto> returningList=new HashSet<>();
        for (SubCategory sCat:subCategories
             ) {
            returningList.add(convertSuperCategoryToSubCategoryDto(sCat));
        }
        return Optional.of(returningList);
    }
        public Optional<Page<ProductDto>> getAllProductsBySuperCategory(String superCategoryName, int offset, int pageSize){
        Pageable paging = PageRequest.of(offset, pageSize);
        String messageIfNotFound="No products found with given super category";
        SuperCategory superCategory = getSuperCategoryByName(superCategoryName,messageIfNotFound);
        List<ProductDto> returningList=new ArrayList<>();
        List<Product> products = superCategory.getProducts();
        for (Product product:products
        ) {
            returningList.add(Utils.convertProductToProductDto(product)) ;
        }
        return Optional.of(Utils.getPageFromList(paging,returningList));
    }

    private SuperCategory getSuperCategoryByName(String name,String message){
        Optional<List<SuperCategory>> superCategoryByName = superCategoryRepository.findByName(name);
        if (superCategoryByName.isPresent()&&!superCategoryByName.get().isEmpty()){
            return superCategoryByName.get().get(0);
        }
        else{
            throw new ResourceNotFoundException(message);
        }
    }
    private SubCategoryDto convertSuperCategoryToSubCategoryDto(SubCategory subCategory){
        SubCategoryDto subCategoryDto=new SubCategoryDto();
        subCategoryDto.setId(subCategory.getSub_cat_id());
        subCategoryDto.setName(subCategory.getName());
        return  subCategoryDto;
    }
    public Page<SuperCategory> findAllSuperCategory(int offset, int pageSize){
        return superCategoryRepository.findAll(PageRequest.of(offset,pageSize));
    }
}
