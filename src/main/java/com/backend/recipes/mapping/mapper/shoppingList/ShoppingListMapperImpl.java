package com.backend.recipes.mapping.mapper.shoppingList;

import com.backend.recipes.dto.shoppingList.ShoppingListDTO;
import com.backend.recipes.mapping.mapper.shoppingListItem.ShoppingListItemMapper;
import com.backend.recipes.model.shoppingList.ShoppingList;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ShoppingListMapperImpl implements ShoppingListMapper{

    private ShoppingListItemMapper shoppingListItemMapper;

    public ShoppingListMapperImpl(ShoppingListItemMapper shoppingListItemMapper) {
        this.shoppingListItemMapper = shoppingListItemMapper;
    }

    @Override
    public ShoppingListDTO mapShoppingListToDTO(ShoppingList shoppingList) {
        return new ShoppingListDTO(
                shoppingList.getId(),
                shoppingList.getName(),
                shoppingList.getDate(),
                shoppingList.getTotalPriceHrk(),
                shoppingList.getTotalPriceEur(),
                shoppingList
                        .getShoppingListItems()
                        .stream()
                        .map(shoppingListItemMapper::mapShoppingListItemToDTO)
                        .collect(Collectors.toList())
        );
    }
}
