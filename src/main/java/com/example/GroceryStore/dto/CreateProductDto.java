package com.example.GroceryStore.dto;

import com.example.GroceryStore.entity.ProductDetails;
import com.example.GroceryStore.entity.ProductSubCategory;

public class CreateProductDto {
    String productName;

    public String getProductName() {
        return productName;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }



    public ProductSubCategory getProductSubCategory() {
        return productSubCategory;
    }


    ProductDetails productDetails;
    ProductSubCategory productSubCategory;

}
