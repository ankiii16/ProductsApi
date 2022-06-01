package com.example.GroceryStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class ProductDto {

     long id;
     String name;
     String s3ImageUrl;
     float price;
     float discountedPrice;
     BigDecimal qrCode;
}
