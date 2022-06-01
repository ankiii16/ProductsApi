package com.example.GroceryStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDescriptionDto {
    long id;
    String title;
    String weight;
    String ingredients;
    String expiry;
    String description;
    String brand;
    String subCategory;
    String superCategory;
    String category;
    float price;
    float discountedPrice;
    String s3ImageUrl;
}
