package com.backend.recipes.service.shoppingListItem;

import com.backend.recipes.command.shoppingListItem.ShoppingListItemCommand;
import com.backend.recipes.model.shoppingListItem.ShoppingListItem;

import java.util.List;
import java.util.Optional;

public interface ShoppingListItemService {
    Optional<ShoppingListItem> update(ShoppingListItemCommand command);
    Optional<ShoppingListItem> findById(Long id);
    List<ShoppingListItem> findAll();

}
