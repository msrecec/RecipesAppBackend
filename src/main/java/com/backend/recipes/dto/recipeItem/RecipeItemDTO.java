package com.backend.recipes.dto.recipeItem;

import com.backend.recipes.dto.ingredient.IngredientDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RecipeItemDTO {
    private Long id;
    private Integer quantity;
    private IngredientDTO ingredientDTO;
}