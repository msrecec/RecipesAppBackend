package com.backend.recipes.mapping.mapper.shoppingList;

import com.backend.recipes.dto.shoppingList.ShoppingListDTO;
import com.backend.recipes.model.ShoppingList;

public interface ShoppingListMapper {
    ShoppingListDTO mapShoppingListToDTO(ShoppingList shoppingList);
}
