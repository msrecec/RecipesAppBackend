package com.backend.recipes.mapping.mapper.recipeItem;

import com.backend.recipes.dto.recipeItem.RecipeItemDTO;
import com.backend.recipes.model.recipeItem.RecipeItem;

public interface RecipeItemMapper {
    RecipeItemDTO mapRecipeItemToDTO(RecipeItem recipeItem);
}
