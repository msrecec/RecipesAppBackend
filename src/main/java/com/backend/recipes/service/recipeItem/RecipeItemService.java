package com.backend.recipes.service.recipeItem;

import com.backend.recipes.command.recipeItem.RecipeItemCommand;
import com.backend.recipes.dto.recipeItem.RecipeItemDTO;

import java.util.List;
import java.util.Optional;

public interface RecipeItemService {
    Optional<RecipeItemDTO> update(RecipeItemCommand command);
    Optional<RecipeItemDTO> findById(Long id);
    List<RecipeItemDTO> findAll();
    void deleteById(Long id);
}
