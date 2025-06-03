package com.technova.shopverse.shopverse_api.dtos;

import jakarta.validation.constraints.*;

public class CategoryModifyDTO {
    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotNull(message = "La descripción no debe ser nulo")
    @NotBlank(message = " La descripción no debe estar vacío")
    @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
    private String description;

    public CategoryModifyDTO(String name, String description) {
        this.name = name;
        this.description = description;
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
}
