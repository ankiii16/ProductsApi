package com.example.GroceryStore.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {

    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", unique = true)
    private long id;

    @Column(name = "name", nullable = false)
    @Size(max = 50, message = "category name cannot be more than 50 character")
    private String name ;

}
