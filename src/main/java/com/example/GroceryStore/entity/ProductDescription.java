package com.example.GroceryStore.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product_description")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_desc_id", unique = true)
    private long id;


    @OneToOne(targetEntity = Description.class,cascade = {CascadeType.ALL})
    @JoinColumn(name="desc_id")
    private Description description;


    @OneToOne(targetEntity = Category.class,cascade = {CascadeType.ALL})
    @JoinColumn(name="category_id")
    private Category category;

    @OneToOne(targetEntity = SubCategory.class,cascade = {CascadeType.ALL})
    @JoinColumn(name="sub_cat_id")
    private SubCategory subCategory;

    @OneToOne(targetEntity = SuperCategory.class,cascade = {CascadeType.ALL})
    @JoinColumn(name="sup_cat_id")
    private SuperCategory superCategory;

    @OneToOne(targetEntity = Brand.class,cascade = {CascadeType.ALL})
    @JoinColumn(name="brand_id")
    private Brand brand;

    @OneToOne(targetEntity = Product.class,cascade = {CascadeType.ALL})
    @JoinColumn(name="product_id")
    private Product product;

//    @OneToMany(cascade = {CascadeType.ALL})
//    private Set<Images> images;

}
