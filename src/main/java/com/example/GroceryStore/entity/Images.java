package com.example.GroceryStore.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "product_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id", unique = true)
    private long id;


    @Column(name = "size", nullable = false)
    private String size;

    @Column(name="image_url",nullable = false)
    private String imageUrl;

//    @ManyToOne
//    @JoinColumn(name="product_desc_id")
//    private ProductDescription productDescription;


}
