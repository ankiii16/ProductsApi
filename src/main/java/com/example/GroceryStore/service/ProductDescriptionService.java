package com.example.GroceryStore.service;

import com.example.GroceryStore.dto.ProductDescriptionDto;
import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.entity.ProductDescription;
import com.example.GroceryStore.exception.ResourceNotFoundException;
import com.example.GroceryStore.repository.ProductDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDescriptionService {

    @Autowired
    ProductDescriptionRepository productDescriptionRepository;
    public Optional<ProductDescriptionDto> getProductDescriptionByProductId(long id){
        Optional<ProductDescription> optionalProductDescription=productDescriptionRepository.findByProductId(id);
        if(optionalProductDescription.isPresent()){
            ProductDescription productDescription=optionalProductDescription.get();

            return Optional.of(getDescriptionDtoByProductDescription(productDescription));
        }
        else{
            throw new ResourceNotFoundException("Product Not Found");
        }
    }
    public Optional<Page<ProductDto>> getProductByPropertyName(String propertyName, String value, int offset, int pageSize){
        Optional<List<ProductDescription>> productDescription=null;
        if(propertyName.equalsIgnoreCase("Brand"))
            productDescription =productDescriptionRepository.findByBrandName(value);
        else if (propertyName.equalsIgnoreCase("Category"))
            productDescription =productDescriptionRepository.findByCategoryName(value);
        else if (propertyName.equalsIgnoreCase("SuperCategory"))
            productDescription =productDescriptionRepository.findBySuperCategoryName(value);
        else
            productDescription =productDescriptionRepository.findBySubCategoryName(value);

        List<ProductDto> returningList=new ArrayList<>();
        if(!productDescription.get().isEmpty() && productDescription.isPresent()){
            List<ProductDescription> p=productDescription.get();
            for (ProductDescription pd:p
            ) {
                returningList.add(getProductDtoByPropertyDescription(pd.getProduct()));
            }


            Pageable paging = PageRequest.of(offset, pageSize);
            int start = Math.min((int)paging.getOffset(), returningList.size());
            int end = Math.min((start + paging.getPageSize()), returningList.size());
            Page<ProductDto> pd=new PageImpl<>(returningList.subList(start,end),paging,productDescription.get().size());
            return Optional.of(pd);
        }
        else{
            throw new ResourceNotFoundException("Product Not Found");
        }

    }


    private ProductDescriptionDto getDescriptionDtoByProductDescription(ProductDescription productDescription){
        ProductDescriptionDto productDescriptionDto =new ProductDescriptionDto();

        productDescriptionDto.setDescription(productDescription.getDescription().getDescriptionAboutProduct());
        productDescriptionDto.setExpiry(productDescription.getDescription().getExpiry());
        productDescriptionDto.setIngredients(productDescription.getDescription().getIngredients());
        productDescriptionDto.setTitle(productDescription.getDescription().getTitle());
        productDescriptionDto.setWeight(productDescription.getDescription().getWeight());
        productDescriptionDto.setId(productDescription.getId());
        productDescriptionDto.setCategory(productDescription.getCategory().getName());
        productDescriptionDto.setBrand(productDescription.getBrand().getName());
        productDescriptionDto.setSubCategory(productDescription.getSubCategory().getName());
        productDescriptionDto.setSuperCategory(productDescription.getSuperCategory().getName());
        productDescriptionDto.setPrice(productDescription.getProduct().getPrice());
        productDescriptionDto.setDiscountedPrice(productDescription.getProduct().getDiscountedPrice());
        productDescriptionDto.setS3ImageUrl(productDescription.getProduct().getS3ImageUrl());
        return productDescriptionDto;
    }
    private ProductDto getProductDtoByPropertyDescription(Product product){
        ProductDto productDto=new ProductDto();
        productDto.setPrice(product.getPrice());
        productDto.setQrCode(product.getQrCode());
        productDto.setName(product.getName());
        productDto.setS3ImageUrl(product.getS3ImageUrl());
        productDto.setId(product.getId());
        productDto.setDiscountedPrice(product.getDiscountedPrice());
        return  productDto;
    }

}
