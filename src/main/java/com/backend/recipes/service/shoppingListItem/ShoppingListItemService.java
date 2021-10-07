package com.backend.recipes.service.shoppingListItem;

import com.backend.recipes.command.shoppingListItem.ShoppingListItemCommand;
import com.backend.recipes.dto.shoppingListItem.ShoppingListItemDTO;
import com.backend.recipes.model.shoppingListItem.ShoppingListItem;

import java.util.List;
import java.util.Optional;

public interface ShoppingListItemService {
    Optional<ShoppingListItemDTO> update(ShoppingListItemCommand command);
    Optional<ShoppingListItemDTO> findById(Long id);
    List<ShoppingListItemDTO> findAll();
    void deleteById(Long id);

}
