package com.backend.recipes.mapping.mapper.shoppingListItem;

import com.backend.recipes.dto.shoppingListItem.ShoppingListItemDTO;
import com.backend.recipes.model.ShoppingListItem;

public interface ShoppingListItemMapper {
    ShoppingListItemDTO mapShoppingListItemToDTO(ShoppingListItem shoppingListItem);
}
