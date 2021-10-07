package com.backend.recipes.service.shoppingList;

import com.backend.recipes.command.recipe.RecipeSaveCommand;
import com.backend.recipes.command.recipe.RecipeUpdateCommand;
import com.backend.recipes.command.shoppingList.ShoppingListSaveCommand;
import com.backend.recipes.command.shoppingList.ShoppingListUpdateCommand;
import com.backend.recipes.dto.recipe.RecipeDTO;
import com.backend.recipes.dto.recipe.RecipeDTOPaginated;
import com.backend.recipes.dto.shoppingList.ShoppingListDTO;
import com.backend.recipes.dto.shoppingList.ShoppingListDTOPaginated;

import java.util.List;
import java.util.Optional;

public interface ShoppingListService {
    List<ShoppingListDTO> findAll();
    Optional<ShoppingListDTO> findById(Long id);
    Optional<ShoppingListDTOPaginated> findPaginated(Integer page);
    Optional<ShoppingListDTO> save(ShoppingListSaveCommand command);
    Optional<ShoppingListDTO> update(ShoppingListUpdateCommand command);
    void deleteById(Long id);
}
