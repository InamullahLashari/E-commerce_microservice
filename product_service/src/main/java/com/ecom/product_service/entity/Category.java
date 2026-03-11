package com.ecom.product_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;
    private String name;
    private String description;

@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<product> Products;
}
