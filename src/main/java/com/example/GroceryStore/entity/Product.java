package com.example.GroceryStore.entity;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
@SecondaryTable(name = "product_details", pkJoinColumns = @PrimaryKeyJoinColumn(name = "product_id"))
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", unique = true)
    private long id;


    //should not be empty
    //should not be less than 2 char and more than 100
    @Column(name = "product_name", nullable = false)
    @NotEmpty(message = "product name cannot be empty")
    @Size(min = 2, message = "product name should be at least 2 character long")
    @Size(max = 100, message = "product name cannot be more than 100 character")
    private String productName;

    @Embedded
    @NotNull(message = "You need to provide product details in ")
    @Valid
    ProductDetails productDetails;

    @Column(name = "product_image_url", nullable = false)
    @NotEmpty(message = "product image cannot be empty")
    @Size(min = 2, message = "product image url should be at least 2 character long")
    private String productImageURL;


    public ProductSubCategory getProductSubCategory() {
        return productSubCategory;
    }

    public void setProductSubCategory(ProductSubCategory productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    @ManyToOne(targetEntity = ProductSubCategory.class,cascade = {CascadeType.ALL})
    @JoinColumn(name = "sc_id")
    private ProductSubCategory productSubCategory;
    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }


    public Product(String productName, ProductDetails productDetails, String productImageURL) {
        this.productName = productName;
        this.productDetails = productDetails;
        this.productImageURL = productImageURL;
    }

    public Product() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

}
