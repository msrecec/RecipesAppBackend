package com.backend.recipes.mapping.mapper.ingredient;

import com.backend.recipes.dto.ingredient.IngredientDTO;
import com.backend.recipes.model.Ingredient;

public interface IngredientMapper {
    IngredientDTO mapIngredientToDTO(Ingredient ingredient);
}
