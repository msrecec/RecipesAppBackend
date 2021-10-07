package com.backend.recipes.command.recipeItem;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RecipeItemCommand {
    @NotNull(message = "Shopping List Item id must not be null")
    @PositiveOrZero(message = "Shopping List Item id must be a positive number or zero")
    private Long id;
    @NotNull(message = "Shopping List Item quantity must not be null")
    @Min(value = 1, message = "Shopping List Item quantity must be at least 1")
    @Max(value = 100000, message = "Shopping List Item quantity must be at most 100000")
    private Integer quantity;
}
