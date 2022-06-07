package com.example.GroceryStore.service;

import com.example.GroceryStore.Utils;
import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.entity.Brand;
import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.exception.ResourceNotFoundException;
import com.example.GroceryStore.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    BrandRepository brandRepository;

    public Optional<Page<ProductDto>> getAllProductsByBrand(String brandName, int offset, int pageSize){
        Pageable paging = PageRequest.of(offset, pageSize);
        Optional<Page<Brand>> productsByBrand = brandRepository.findByName(brandName,paging);
        List<ProductDto> returningList=new ArrayList<>();
        if(productsByBrand.isPresent()){
            List<Product> products = productsByBrand.get().getContent().get(0).getProducts();
            for (Product product:products
                 ) {
                returningList.add(Utils.convertProductToProductDto(product));
            }
            int start = Math.min((int)paging.getOffset(), returningList.size());
            int end = Math.min((start + paging.getPageSize()), returningList.size());
            Page<ProductDto> pd=new PageImpl<>(returningList.subList(start,end),paging,returningList.size());
            return Optional.of(pd);
        }
        else{
            throw new ResourceNotFoundException("No Brand Found");
        }
    }

    public Page<Brand> getAllBrands(int offset, int pageSize){
        return this.brandRepository.findAll(PageRequest.of(offset,pageSize));
    }

}
