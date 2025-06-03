package com.technova.shopverse.shopverse_api.services;

import com.technova.shopverse.shopverse_api.dtos.CategoryDTO;
import com.technova.shopverse.shopverse_api.dtos.CategoryModifyDTO;
import com.technova.shopverse.shopverse_api.exceptions.CategoryNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<List<CategoryDTO>> listAllCatDTOs();
    //ResponseEntity<Category> getCatById(Long id) throws CategoryNotFoundException;
    ResponseEntity<CategoryDTO> getCatDTOById(Long id) throws CategoryNotFoundException;
    ResponseEntity<String> registerCat(CategoryModifyDTO cat);
    ResponseEntity<String> updateCat(Long id, CategoryModifyDTO cat) throws CategoryNotFoundException;
    ResponseEntity<Void> deleteCat(Long id) throws CategoryNotFoundException;
}
