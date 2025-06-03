package com.technova.shopverse.shopverse_api.controllers;

import com.technova.shopverse.shopverse_api.dtos.ProductDTO;
import com.technova.shopverse.shopverse_api.dtos.ProductModifyDTO;
import com.technova.shopverse.shopverse_api.exceptions.CategoryNotFoundException;
import com.technova.shopverse.shopverse_api.exceptions.ProductNotFoundException;
import com.technova.shopverse.shopverse_api.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    //private final Pro
    @Autowired
    private ProductService prodServ;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProductsWithCategories() {
        return prodServ.listAllProdDTOs();
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getByCategoryId(@PathVariable Long categoryId) {
        return prodServ.getByCategoryId(categoryId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        return prodServ.getProdDTOById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> createProd(@Valid @RequestBody ProductModifyDTO product) throws CategoryNotFoundException {
        return prodServ.registerProd(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductModifyDTO updProd)
            throws ProductNotFoundException, CategoryNotFoundException {
        return prodServ.updateProd(id,updProd);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        return prodServ.deleteProd(id);
    }


}
