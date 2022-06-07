package com.example.GroceryStore.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", unique = true)
    private long category_id;

    @Column(name = "name")
    private String name ;


    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonIgnore
    @JoinTable(name="category_super_categories",joinColumns = {@JoinColumn(name="category_id")},
            inverseJoinColumns = {@JoinColumn(name="sup_cat_id")}
    )
    private Set<SuperCategory> superCategories =new HashSet<>();

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonIgnore
    @JoinTable(name="product_by_category",joinColumns = {@JoinColumn(name="category_id")},
            inverseJoinColumns = {@JoinColumn(name="product_id")}
    )
    private List<Product> products=new ArrayList<>();


    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonIgnore
    @JoinTable(name="brand_by_category",joinColumns = {@JoinColumn(name="category_id")},
            inverseJoinColumns = {@JoinColumn(name="brand_id")}
    )
    private Set<Brand> brands=new HashSet<>();


}
