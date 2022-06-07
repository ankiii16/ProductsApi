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

@Entity(name = "brand")
@Table(name = "brand")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Brand  {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "brand_id", unique = true,nullable = false,updatable = false)
    public long id;

    @Column(name = "name", nullable = false)
    @Size(max = 50, message = "brand name cannot be more than 50 character")
    public String name;


    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonIgnore
    @JoinTable(name="product_by_brand",joinColumns = {@JoinColumn(name="brand_id")},
            inverseJoinColumns = {@JoinColumn(name="product_id")}
    )
    private List<Product> products=new ArrayList<>();

}
