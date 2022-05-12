package com.example.GroceryStore.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product_sub_category")
public class ProductSubCategory {

    public ProductSubCategory(String title) {
        this.title = title;
    }

    public ProductSubCategory() {
    }

    public ProductSubCategory(String title, ProductCategory productCategory) {
        this.title = title;
        this.productCategory = productCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sc_id", unique = true)
    private long id;

    @Column(name = "title")
    @NotEmpty(message = "product image cannot be empty")
    @Size(min = 2, message = "Sub category title cannot be empty")
    private String title;

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    @ManyToOne(targetEntity = ProductCategory.class,cascade = {CascadeType.ALL})
    @JoinColumn(name = "c_id")
    private ProductCategory productCategory;

}
