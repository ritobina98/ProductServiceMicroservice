package com.ritobina.productservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    private String title;
    private Double price;
    private String description;
    private String imgUrl;
    @ManyToOne(cascade = {jakarta.persistence.CascadeType.PERSIST})
    @JoinColumn
    @JsonManagedReference
    private Category category;

}
