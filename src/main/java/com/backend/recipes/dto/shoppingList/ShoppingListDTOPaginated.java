package com.backend.recipes.dto.shoppingList;

import com.backend.recipes.dto.recipe.RecipeDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ShoppingListDTOPaginated {
    List<ShoppingListDTO> shoppingListDTO;
    long totalPages;
    long totalElements;
}
