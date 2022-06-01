package com.example.GroceryStore.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity(name="products")
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true)
    private long id;


    //should not be empty
    //should not be less than 2 char and more than 100
    @Column(name = "name", nullable = false)
    @NotEmpty(message = "product name cannot be empty")
    @Size(min = 2, message = "product name should be at least 2 character long")
    @Size(max = 100, message = "product name cannot be more than 100 character")
    private String name;


    @Column(name = "image_url", nullable = false)
    @NotEmpty(message = "product image cannot be empty")
    @Size(min = 2, message = "product image url should be at least 2 character long")
    private String s3ImageUrl;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "discounted_price", nullable = false)
    private float discountedPrice;

    @Column(name="qr_code",nullable = false)
    private BigDecimal qrCode;



}
