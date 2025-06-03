package com.technova.shopverse.shopverse_api.services.impl;

import com.technova.shopverse.shopverse_api.dtos.CategoryDTO;
import com.technova.shopverse.shopverse_api.dtos.CategoryModifyDTO;
import com.technova.shopverse.shopverse_api.exceptions.CategoryNotFoundException;
import com.technova.shopverse.shopverse_api.model.Category;
import com.technova.shopverse.shopverse_api.repositories.CategoryRepository;
import com.technova.shopverse.shopverse_api.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository catRepo;

    @Override
    public ResponseEntity<List<CategoryDTO>> listAllCatDTOs() {
        return Optional.of(
                    catRepo.findAll().stream().map(CategoryDTO::new).toList()
                )
                .filter(categories -> !categories.isEmpty())
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT)
                        // orElseGet() porque no se requiere un Throw y el metodo normal no sirve.
                );
    }

    //@Override
    public ResponseEntity<Category> getCatById(Long id) throws CategoryNotFoundException {
        return catRepo.findById(id).map(ResponseEntity::ok).orElseThrow(
                () -> new CategoryNotFoundException("Categoria no encontrada con el id: " + id));
    }

    @Override
    public ResponseEntity<CategoryDTO> getCatDTOById(Long id) throws CategoryNotFoundException {
       return catRepo.findById(id).map(CategoryDTO::new).map(ResponseEntity::ok).orElseThrow(
                () -> new CategoryNotFoundException("Categoria no encontrada con el id: " + id));
    }

    //@Override
    public Category createCat(CategoryModifyDTO dto) {
        return new Category(dto.getName(), dto.getDescription());
    }

    //@Override
    public void saveCat(Category cat) {
        catRepo.save(cat);
    }

    @Override
    public ResponseEntity<String> registerCat(CategoryModifyDTO cat) {
        //validateData(cat);
        saveCat(createCat(cat));
        // ¿Existiran casos de borde que puedan saltar el error de Save?
        return new ResponseEntity<>("Se creo la categoria.", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> updateCat(Long id, CategoryModifyDTO updCat)
            throws CategoryNotFoundException {
        Category cat = getCatById(id).getBody();

        if (cat != null) {
            //validateData(updCat);
            cat.setName(updCat.getName());
            cat.setDescription(updCat.getDescription());
            saveCat(cat);
            return new ResponseEntity<>("Se actualizo la categoria", HttpStatus.OK);
        }
        return new ResponseEntity<>("No se encontro la categoria", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Void> deleteCat(Long id) throws CategoryNotFoundException {
        Category cat = getCatById(id).getBody();

        if (cat != null && cat.getProducts().isEmpty()) {
            catRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (cat != null && !cat.getProducts().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*public void validateData(CategoryDTO dto) throws InvalidDataFromCategoryException {
        if (dto == null) {
            throw new InvalidDataFromCategoryException("La categoria no puede estar vacia.");
        }
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new InvalidDataFromCategoryException("El nombre no puede estar vacio.");
        }
        if (dto.getDescription() == null || dto.getDescription().isBlank() || dto.getDescription().length() < 10) {
            throw new InvalidDataFromCategoryException("La descripcion debe tener al menos 10 caracteres.");
        }
    }*/

    /*public CategoryDTO toDTO(Category cat) {
        List<String> productNames = cat.getProducts().stream() // Stream/Flujo de tipo ARRAY en JS
                .map(Product::getName).toList();
        return new CategoryDTO(cat.getId(), cat.getName(),
                cat.getDescription(), productNames);
    }*/

}
