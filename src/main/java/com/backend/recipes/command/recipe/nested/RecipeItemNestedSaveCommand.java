package com.backend.recipes.command.recipe.nested;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RecipeItemNestedSaveCommand {
    @NotNull(message = "Recipe id must not be null")
    @PositiveOrZero(message = "Recipe id must be a positive number or zero")
    private Long id;
    @NotNull(message = "Recipe item quantity must not be null")
    @PositiveOrZero(message = "Recipe item quantity must be a positive number or zero")
    private Integer quantity;
}
