package com.example.GroceryStore.service;

import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.exception.ResourceNotFoundException;
import com.example.GroceryStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        Optional<Product> productByCode = productRepository.findByProductDetailsProductQRCode(product.getProductDetails().getProductQRCode());
        if (productByCode.isPresent()) {
            throw new IllegalStateException("Product already Exits");
        }
        return productRepository.save(product);
    }

    @Transactional
    public Optional<Product> deleteProduct(BigDecimal productQRCode) {
        Optional<Product> productByCode = productRepository.findByProductDetailsProductQRCode(productQRCode);
        if (productByCode.isPresent()) {
            productRepository.deleteByProductDetailsProductQRCode(productQRCode);
        } else {
            throw new ResourceNotFoundException("Product Not Found");
        }
        return productByCode;
    }

    public Optional<Product> getProductByQRCode(BigDecimal productQRCode) {
        Optional<Product> productByCode = productRepository.findByProductDetailsProductQRCode(productQRCode);
        if (productByCode.isPresent()) {
            return productByCode;
        } else {
            throw new ResourceNotFoundException("Product Not Found");
        }
    }

    @Transactional
    public Product updateProduct(BigDecimal productQRCode, BigDecimal productPrice) {
        Product product = productRepository.findByProductDetailsProductQRCode(productQRCode).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        if (!Objects.equals(product.getProductDetails().getProductPrice(), productPrice)) {
            product.getProductDetails().setProductPrice(productPrice);
        }
        return product;
    }
}
