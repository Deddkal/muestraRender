package com.technova.shopverse.shopverse_api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Para generar el ID, no debe estar definido dentro del constructor.

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotNull(message = "La descripción no debe ser nulo")
    @NotBlank(message = " La descripción no debe estar vacío")
    @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
    private String description;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<Product> products;
    // Se sugiere cambiar los List por Set,

    public Category() {}

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProduct(Product product) {
        this.products.add(product);
    }
}
