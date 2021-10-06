package com.backend.recipes.dto.shoppingListItem;

import com.backend.recipes.dto.ingredient.IngredientDTO;
import com.backend.recipes.dto.shoppingList.ShoppingListDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ShoppingListItemDTO {
    private Long id;
    private Integer quantity;
    private IngredientDTO ingredientDTO;
}
