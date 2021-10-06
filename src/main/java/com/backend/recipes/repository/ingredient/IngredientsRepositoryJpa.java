package com.backend.recipes.repository.ingredient;

import com.backend.recipes.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepositoryJpa extends JpaRepository<Ingredient, Long> {

}
