package com.technova.shopverse.shopverse_api.batch.model;

public class CategoryCsv {
    //@NotNull(message = "El nombre no puede ser nulo")
    //@NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    //@NotNull(message = "La descripción no debe ser nulo")
    //@NotBlank(message = " La descripción no debe estar vacío")
    //@Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
    private String description;

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
