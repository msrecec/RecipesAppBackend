package com.backend.recipes.command.shoppingList;

import com.backend.recipes.command.shoppingList.nested.ShoppingListItemNestedSaveCommand;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ShoppingListUpdateCommand {
    @NotNull(message = "Shopping list id must not be null")
    @PositiveOrZero(message = "Shopping list id must be a positive number or zero")
    private Long id;
    @NotNull(message = "Shopping list name must not be null")
    @NotEmpty(message = "Shopping list name must not be empty")
    private String name;
    @NotNull(message = "Shopping list date must not be null")
    private Date date;
    @Valid
    List<ShoppingListItemNestedSaveCommand> shoppingListItems;
}