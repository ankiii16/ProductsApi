package com.example.GroceryStore.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "sub_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "sub_cat_id", unique = true)
    private long id;

    @Column(name = "name", nullable = false)
    @Size(max = 50, message = "subcategory name cannot be more than 50 character")
    private String name;

}
