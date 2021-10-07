package com.backend.recipes.command.recipe;


import com.backend.recipes.command.recipe.nested.RecipeItemNestedSaveCommand;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RecipeUpdateCommand {
    @NotNull(message = "Recipe id must not be null")
    @PositiveOrZero(message = "Recipe id must be a positive number or zero")
    private Long id;
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
