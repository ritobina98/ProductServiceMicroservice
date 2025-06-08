package com.ritobina.productservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel{
    @Column(unique = true, nullable = false)
    private String title;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    @JsonBackReference
    //@JsonIgnore
    private List<Product> products;

}
