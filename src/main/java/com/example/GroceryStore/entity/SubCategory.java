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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sub_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sub_cat_id", unique = true)
    private long sub_cat_id;

    @Column(name = "name", nullable = false)
    @Size(max = 50, message = "subcategory name cannot be more than 50 character")
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonIgnore
    @JoinTable(name="product_by_sub_category",joinColumns = {@JoinColumn(name="sub_cat_id",nullable = true)},
            inverseJoinColumns = {@JoinColumn(name="product_id")}
    )
    private List<Product> products=new ArrayList<>();

}
