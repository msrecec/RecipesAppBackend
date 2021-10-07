package com.backend.recipes.command.recipe;

import com.backend.recipes.command.recipe.nested.RecipeItemNestedSaveCommand;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RecipeSaveCommand {
    @NotNull(message = "Recipe name must not be null")
    @Size(min = 1, max = 100, message = "Recipe name must be between(including) 1 and 100 characters")
    private String name;
    @NotNull(message = "Recipe description name must not be null")
    @Size(min = 1, max = 1000, message = "Recipe description must be between(including) 1 and 1000 characters")
    private String shortDescription;
    @NotNull(message = "Recipe description name must not be null")
    @Size(min = 1, max = 1000, message = "Recipe description must be between(including) 1 and 1000 characters")
    private String description;
    @Valid
    List<RecipeItemNestedSaveCommand> recipeItems;
}
