package com.backend.recipes.mapping.mapper.shoppingListItem;

import com.backend.recipes.dto.shoppingListItem.ShoppingListItemDTO;
import com.backend.recipes.mapping.mapper.ingredient.IngredientMapper;
import com.backend.recipes.model.shoppingListItem.ShoppingListItem;
import org.springframework.stereotype.Component;

@Component
public class ShoppingListItemMapperImpl implements ShoppingListItemMapper{

    private IngredientMapper ingredientMapper;

    public ShoppingListItemMapperImpl(IngredientMapper ingredientMapper) {
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public ShoppingListItemDTO mapShoppingListItemToDTO(ShoppingListItem shoppingListItem) {
        return new ShoppingListItemDTO(shoppingListItem.getId(), shoppingListItem.getQuantity(), ingredientMapper.mapIngredientToDTO(shoppingListItem.getIngredient()));
    }
}
