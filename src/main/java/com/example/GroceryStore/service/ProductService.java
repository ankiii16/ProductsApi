package com.example.GroceryStore.service;

import com.example.GroceryStore.entity.Product;
import com.example.GroceryStore.exception.ResourceNotFoundException;
import com.example.GroceryStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        Optional<Product> productByCode = productRepository.findByProductQRCode(product.getProductQRCode());
        if (productByCode.isPresent()) {
            throw new IllegalStateException("Product already Exits");
        }
        return productRepository.save(product);
    }

    @Transactional
    public Optional<Product> deleteProduct(Long productQRCode) {
        Optional<Product> productByCode = productRepository.findByProductQRCode(productQRCode);
        if (productByCode.isPresent()) {
            productRepository.deleteByProductQRCode(productQRCode);
        } else {
            throw new ResourceNotFoundException("Product Not Found");
        }
        return productByCode;
    }

    public Optional<Product> getProductByQRCode(long productQRCode) {
        Optional<Product> productByCode = productRepository.findByProductQRCode(productQRCode);
        if (productByCode.isPresent()) {
            return productByCode;
        } else {
            throw new ResourceNotFoundException("Product Not Found");
        }
    }

    @Transactional
    public Product updateProduct(long productQRCode, float productPrice) {
        Product product = productRepository.findByProductQRCode(productQRCode).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        if (productPrice != 0 && !Objects.equals(product.getProductPrice(), productPrice)) {
            product.setProductPrice(productPrice);
        }
        return product;
    }
}
