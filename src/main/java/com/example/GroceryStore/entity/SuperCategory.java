package com.example.GroceryStore.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "super_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class SuperCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sup_cat_id", unique = true)
    public long sup_cat_id;

    @Column(name = "name")
    @Size(max = 50, message = "super category name cannot be more than 50 character")
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonIgnore
    @JoinTable(name="super_sub_categories",joinColumns = {@JoinColumn(name="sup_cat_id")},
            inverseJoinColumns = {@JoinColumn(name="sub_cat_id",nullable = true)}
    )
    private Set<SubCategory> subCategories =new HashSet<>();


    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonIgnore
    @JoinTable(name="product_by_super_category",joinColumns = {@JoinColumn(name="sup_cat_id")},
            inverseJoinColumns = {@JoinColumn(name="product_id")}
    )
    private List<Product> products=new ArrayList<>();
}
