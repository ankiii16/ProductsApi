package com.example.GroceryStore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "description")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Description {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "desc_id", unique = true)
    private long id;



    @Column(name = "title", nullable = false)
    @Size(max = 100, message = "product title cannot be more than 100 character")
    private String title;


    @Column(name = "weight", nullable = false)
    private String weight;

    @Column(name = "ingredients", nullable = false,columnDefinition="LONGTEXT")
    private String ingredients;

    @Column(name = "expiry", nullable = false)
    private String expiry;

    @Column(name = "description_about_product", nullable = false, columnDefinition="LONGTEXT")
    private String descriptionAboutProduct;





}
