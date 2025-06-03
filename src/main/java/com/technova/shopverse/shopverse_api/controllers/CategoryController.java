package com.technova.shopverse.shopverse_api.controllers;

import com.technova.shopverse.shopverse_api.dtos.CategoryDTO;
import com.technova.shopverse.shopverse_api.dtos.CategoryModifyDTO;
import com.technova.shopverse.shopverse_api.exceptions.CategoryNotFoundException;
import com.technova.shopverse.shopverse_api.exceptions.InvalidDataFromCategoryException;
import com.technova.shopverse.shopverse_api.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService catServ;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return catServ.listAllCatDTOs();
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) throws CategoryNotFoundException {
        return catServ.getCatById(id);
    }*/

    @GetMapping("/{id}/details")
    public ResponseEntity<CategoryDTO> getCategoryDetails(@PathVariable Long id) throws CategoryNotFoundException {
        return catServ.getCatDTOById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryModifyDTO category)
            throws InvalidDataFromCategoryException {
        return catServ.registerCat(category);
    }

    // se puede usar el <?> para indicar que el tipo puede variar
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryModifyDTO updCat)
            throws InvalidDataFromCategoryException, CategoryNotFoundException {
        return catServ.updateCat(id,updCat);
    }

    // HTTP 418 al intentar eliminar Categoria con Productos asociados.
    // Hasta que se decida si se deben eliminar los productos, dejar sin asociacion o no hacer nada.
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws CategoryNotFoundException {
        return catServ.deleteCat(id);
    }

}
