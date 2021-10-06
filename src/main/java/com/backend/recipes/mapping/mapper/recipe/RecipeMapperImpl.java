package com.backend.recipes.mapping.mapper.recipe;

import com.backend.recipes.dto.recipe.RecipeDTO;
import com.backend.recipes.mapping.mapper.recipeItem.RecipeItemMapper;
import com.backend.recipes.model.recipe.Recipe;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RecipeMapperImpl implements RecipeMapper{
    private RecipeItemMapper recipeItemMapper;

    public RecipeMapperImpl(RecipeItemMapper recipeItemMapper) {
        this.recipeItemMapper = recipeItemMapper;
    }

    @Override
    public RecipeDTO mapRecipeToDTO(Recipe recipe) {
        return new RecipeDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getShortDescription(),
                recipe.getDescription(),
                recipe.getDate(),
                recipe.getTotalPriceHrk(),
                recipe.getTotalPriceEur(),
                recipe
                        .getRecipeItems()
                        .stream()
                        .map(this.recipeItemMapper::mapRecipeItemToDTO)
                        .collect(Collectors.toList())
        );
    }
}
