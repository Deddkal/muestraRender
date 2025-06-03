package com.technova.shopverse.shopverse_api.repositories;

import com.technova.shopverse.shopverse_api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
