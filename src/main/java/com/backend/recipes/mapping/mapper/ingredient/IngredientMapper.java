package com.backend.recipes.mapping.mapper.ingredient;

import com.backend.recipes.dto.ingredient.IngredientDTO;
import com.backend.recipes.dto.ingredient.IngredientDTOPaginated;
import com.backend.recipes.model.ingredient.Ingredient;

import java.util.List;

public interface IngredientMapper {
    IngredientDTO mapIngredientToDTO(Ingredient ingredient);
}
