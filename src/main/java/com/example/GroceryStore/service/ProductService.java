package com.example.GroceryStore.service;

import com.example.GroceryStore.Utils;
import com.example.GroceryStore.dto.ProductDto;
import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.exception.ResourceNotFoundException;
import com.example.GroceryStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;


    public Page<Product> getAllProducts(int offset, int pageSize) {
        return productRepository.findAll(PageRequest.of(offset,pageSize));
    }

    public Optional<Page<ProductDto>> getAllProductsByName(int offset, int pageSize, String name) {
        Pageable paging = PageRequest.of(offset, pageSize);
        Optional<Page<Product>> productByNameContaining = productRepository.findByNameLike(name,paging);
        List<ProductDto> returningList=new ArrayList<>();
        if(productByNameContaining.isPresent()){
            List<Product> products = productByNameContaining.get().getContent();
            for (Product product:products
            ) {
                returningList.add(Utils.convertProductToProductDto(product)) ;
            }
            return Optional.of(Utils.getPageFromList(paging,returningList));
        }
        else{
            throw new ResourceNotFoundException("No product found with following keyword");
        }
    }
}