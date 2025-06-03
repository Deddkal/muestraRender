package com.technova.shopverse.shopverse_api.dtos;

import com.technova.shopverse.shopverse_api.model.Category;
import com.technova.shopverse.shopverse_api.model.Product;
import jakarta.validation.constraints.*;

import java.util.List;

public class CategoryDTO {
    private Long id;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotNull(message = "La descripción no debe ser nulo")
    @NotBlank(message = " La descripción no debe estar vacío")
    @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
    private String description;

    private List<String> productsNames;

    public CategoryDTO(Category cat) {
        this.id = cat.getId();
        this.name = cat.getName();
        this.description = cat.getDescription();
        this.productsNames = cat.getProducts().stream() // Stream/Flujo de tipo ARRAY en JS
                .map(Product::getName).toList();
    }

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

    public List<String> getProductsNames() {
        return productsNames;
    }

    public void setProductsNames(List<String> productsNames) {
        this.productsNames = productsNames;
    }
}
