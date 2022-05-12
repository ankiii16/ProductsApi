package com.example.GroceryStore.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product_category")
public class ProductCategory {
    public ProductCategory(String title) {
        this.title = title;
    }

    public ProductCategory() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "c_id", unique = true)
    private long id;

    @Column(name = "title")
    @NotEmpty(message = "product image cannot be empty")
    @Size(min = 2, message = "Sub category title cannot be empty")
    private String title;
}
