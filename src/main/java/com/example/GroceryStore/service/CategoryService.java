package com.example.GroceryStore.service;

import com.example.GroceryStore.Utils;
import com.example.GroceryStore.dto.ProductDescriptionDto;
import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.dto.SuperCategoryDto;
import com.example.GroceryStore.entity.Category;
import com.example.GroceryStore.entity.Description;
import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.entity.SuperCategory;
import com.example.GroceryStore.exception.ResourceNotFoundException;
import com.example.GroceryStore.repository.CategoryRepository;
import com.example.GroceryStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Optional<Page<ProductDto>> getAllProductsByCategory(String categoryName, int offset, int pageSize){

        Pageable paging = PageRequest.of(offset, pageSize);
        Optional<List<Category>> categoryByName = categoryRepository.findProductsByName(categoryName);
        List<ProductDto> returningList=new ArrayList<>();
        if(!categoryByName.isEmpty() && categoryByName.isPresent()&&!categoryByName.get().isEmpty()){
            Category category = categoryByName.get().get(0);
            List<Product> products = category.getProducts();
            for (Product product:products
            ) {
                returningList.add(Utils.convertProductToProductDto(product)) ;
            }
            int start = Math.min((int)paging.getOffset(), returningList.size());
            int end = Math.min((start + paging.getPageSize()), returningList.size());
            Page<ProductDto> pd=new PageImpl<>(returningList.subList(start,end),paging,returningList.size());
            return Optional.of(pd);
        }
        else{
            throw new ResourceNotFoundException("Category Not Found");
        }


    }
    public Optional<Set<SuperCategoryDto>> getAllSuperCategoriesByCategory(String categoryName){
        Optional<Category> categoryByName = categoryRepository.findSuperCategoriesByName(categoryName);
        Set<SuperCategoryDto> superCategoryDtos=new HashSet<>();
        if(!categoryByName.isEmpty() && categoryByName.isPresent()){
            Category category = categoryByName.get();
            Set<SuperCategory> superCategories = category.getSuperCategories();
            for (SuperCategory supCat:superCategories
            ) {
                superCategoryDtos.add(convertSuperCategoryToSuperCategoryDto(supCat));
            }
            return Optional.of(superCategoryDtos);
        }
        else{
            throw new ResourceNotFoundException("Category Not Found");
        }
    }


    public Optional<ProductDescriptionDto> getProductDescById(long id){
        Optional<String> categoryByProductId = categoryRepository.findCategoryNameByProductId(id);
        Optional<String> superCategoryNameByProductId = categoryRepository.findSuperCategoryNameByProductId(id);
        Optional<String> subCategoryNameByProductId = categoryRepository.findSubCategoryNameByProductId(id);
        Optional<String> brandNameByProductId=categoryRepository.findBrandNameByProductId(id);
        Optional<Product> descriptionByProductId=productRepository.findById(id);

        ProductDescriptionDto productDescriptionDto=new ProductDescriptionDto();
        if(descriptionByProductId.isPresent()&&brandNameByProductId.isPresent()&&categoryByProductId.isPresent()&&superCategoryNameByProductId.isPresent()&&subCategoryNameByProductId.isPresent()){
            Description description=descriptionByProductId.get().getDescription();
            String category="";
            String subCategory="";
            String superCategory="";
            String brand="";
            if(categoryByProductId.isPresent()&&!categoryByProductId.get().isEmpty())
                category=categoryByProductId.get();
            if(subCategoryNameByProductId.isPresent()&&!subCategoryNameByProductId.get().isEmpty()){
                subCategory=subCategoryNameByProductId.get();
            }
            if(superCategoryNameByProductId.isPresent()&& !superCategoryNameByProductId.get().isEmpty()){
                superCategory=superCategoryNameByProductId.get();
            }
            if(brandNameByProductId.isPresent()&&!brandNameByProductId.get().isEmpty()){
                brand=brandNameByProductId.get();
            }
            productDescriptionDto.setDescription(description.getDescriptionAboutProduct());
            productDescriptionDto.setSuperCategory(superCategory);
            productDescriptionDto.setSubCategory(subCategory);
            productDescriptionDto.setCategory(category);
            productDescriptionDto.setBrand(brand);
            productDescriptionDto.setS3ImageUrl(descriptionByProductId.get().getS3ImageUrl());
            productDescriptionDto.setExpiry(description.getExpiry());
            productDescriptionDto.setId(description.getId());
            productDescriptionDto.setIngredients(description.getIngredients());
            productDescriptionDto.setDiscountedPrice(descriptionByProductId.get().getDiscountedPrice());
            productDescriptionDto.setPrice(descriptionByProductId.get().getPrice());
            productDescriptionDto.setTitle(description.getTitle());
            productDescriptionDto.setWeight(description.getWeight());
            return Optional.of(productDescriptionDto);
        }
        else{
            throw new ResourceNotFoundException("Description not found");
        }
    }

    public SuperCategoryDto convertSuperCategoryToSuperCategoryDto(SuperCategory superCategory){
        SuperCategoryDto superCategoryDto=new SuperCategoryDto();
        superCategoryDto.setName(superCategory.getName());
        superCategoryDto.setId(superCategory.getSup_cat_id());
        return superCategoryDto;
    }

}
