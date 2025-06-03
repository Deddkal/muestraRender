package com.technova.shopverse.shopverse_api.dtos;

/*public record ProductDTO (Long id, String name, String description, Double price) {
//public record ProductsDTO (name = ) {
    //Genera un objeto de forma rapida, como GETTER
    //No genera SETTERS, ya que es un Objeto Inmutable.
    //public ProductDTO createPerson(@RequestBody NewProductDTO prodDTO) {
    //    ProductDTO proDto = new Product(proDTO.name());
    //}
}*/

import com.technova.shopverse.shopverse_api.model.Product;
import jakarta.validation.constraints.*;

public class ProductDTO {
    private Long id;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String name;

    @NotNull(message = "La descripción no debe ser nulo")
    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Double price;

    @NotNull(message = "La categoría es obligatoria")
    @NotBlank(message = "La categoría no puede estar vacía")
    private String categoryName;

    public ProductDTO(Product prod) {
        this.id = prod.getId();
        this.name = prod.getName();
        this.description = prod.getDescription();
        this.price = prod.getPrice();
        this.categoryName = prod.getCategory() != null
                ? prod.getCategory().getName() : null;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}