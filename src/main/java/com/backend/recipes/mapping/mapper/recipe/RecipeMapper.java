package com.backend.recipes.mapping.mapper.recipe;

import com.backend.recipes.dto.recipe.RecipeDTO;
import com.backend.recipes.model.recipe.Recipe;

public interface RecipeMapper {
    RecipeDTO mapRecipeToDTO(Recipe recipe);
}
