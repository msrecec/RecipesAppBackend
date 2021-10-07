package com.backend.recipes.service.recipe;

import com.backend.recipes.command.recipe.RecipeSaveCommand;
import com.backend.recipes.dto.recipe.RecipeDTO;
import com.backend.recipes.dto.recipe.RecipeDTOPaginated;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    List<RecipeDTO> findAll();
    Optional<RecipeDTO> findById(Long id);
    Optional<RecipeDTOPaginated> findByPage(Integer page);
    Optional<RecipeDTO> save(RecipeSaveCommand command);
    void deleteById(Long id);
}
