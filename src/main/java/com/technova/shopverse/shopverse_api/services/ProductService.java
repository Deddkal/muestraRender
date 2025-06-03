package com.technova.shopverse.shopverse_api.services;

import com.technova.shopverse.shopverse_api.dtos.ProductDTO;
import com.technova.shopverse.shopverse_api.dtos.ProductModifyDTO;
import com.technova.shopverse.shopverse_api.exceptions.CategoryNotFoundException;
import com.technova.shopverse.shopverse_api.exceptions.ProductNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<List<ProductDTO>> listAllProdDTOs();
    ResponseEntity<List<ProductDTO>> getByCategoryId(Long categoryId);
    ResponseEntity<ProductDTO> getProdDTOById(Long id) throws ProductNotFoundException;
    ResponseEntity<String> registerProd(ProductModifyDTO prod) throws CategoryNotFoundException;
    ResponseEntity<String> updateProd(Long id, ProductModifyDTO updProd)
            throws ProductNotFoundException, CategoryNotFoundException;
    ResponseEntity<Void> deleteProd(Long id) throws ProductNotFoundException;
}
