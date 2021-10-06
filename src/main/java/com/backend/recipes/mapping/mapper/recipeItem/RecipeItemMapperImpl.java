package com.backend.recipes.mapping.mapper.recipeItem;

import com.backend.recipes.dto.recipeItem.RecipeItemDTO;
import com.backend.recipes.mapping.mapper.ingredient.IngredientMapper;
import com.backend.recipes.model.recipeItem.RecipeItem;
import org.springframework.stereotype.Component;

@Component
public class RecipeItemMapperImpl implements RecipeItemMapper{
    private IngredientMapper ingredientMapper;

    public RecipeItemMapperImpl(IngredientMapper ingredientMapper) {
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public RecipeItemDTO mapRecipeItemToDTO(RecipeItem recipeItem) {
        return new RecipeItemDTO(recipeItem.getId(), recipeItem.getQuantity(), ingredientMapper.mapIngredientToDTO(recipeItem.getIngredient()));
    }
}
