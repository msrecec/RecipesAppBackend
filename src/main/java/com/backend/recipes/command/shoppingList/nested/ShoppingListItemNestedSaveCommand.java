package com.backend.recipes.command.shoppingList.nested;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ShoppingListItemNestedSaveCommand {
    @NotNull(message = "Shopping list id must not be null")
    @PositiveOrZero(message = "Shopping list id must be a positive number or zero")
    private Long id;
    @NotNull(message = "Shopping list quantity must not be null")
    @PositiveOrZero(message = "Shopping list quantity must be a positive number or zero")
    private Integer quantity;
}
