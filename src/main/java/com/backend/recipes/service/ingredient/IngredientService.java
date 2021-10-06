package com.backend.recipes.service.ingredient;

import com.backend.recipes.command.ingredient.IngredientSaveCommand;
import com.backend.recipes.command.ingredient.IngredientUpdateCommand;
import com.backend.recipes.dto.ingredient.IngredientDTO;
import com.backend.recipes.dto.ingredient.IngredientDTOPaginated;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    Optional<IngredientDTO> save(IngredientSaveCommand ingredientSaveCommand);
    List<IngredientDTO> findAll();
    Optional<IngredientDTOPaginated> findPaginated(Integer page);
    Optional<IngredientDTO> findById(Long id);
    Optional<IngredientDTO> update(IngredientUpdateCommand ingredientUpdateCommand);
    void deleteById(Long id);
}
