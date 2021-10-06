package com.backend.recipes.repository.ingredient;

import com.backend.recipes.model.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepositoryJpa extends JpaRepository<Ingredient, Long> {

}
