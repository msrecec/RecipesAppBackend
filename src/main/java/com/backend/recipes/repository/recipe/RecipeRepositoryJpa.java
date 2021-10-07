package com.backend.recipes.repository.recipe;

import com.backend.recipes.model.recipe.Recipe;
import com.backend.recipes.model.recipeItem.RecipeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepositoryJpa extends JpaRepository<Recipe, Long> {
}
