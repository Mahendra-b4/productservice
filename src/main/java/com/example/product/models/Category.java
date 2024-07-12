package com.example.product.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@JsonSerialize
public class Category extends BaseModel implements Serializable {

    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> productList;
}
