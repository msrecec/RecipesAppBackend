package com.backend.recipes.command.ingredient;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class IngredientUpdateCommand {
    @NotNull(message = "Ingredient id must not be null")
    @PositiveOrZero(message = "Ingredient id must be a positive number or zero")
    private Long id;
    @NotNull(message = "Ingredient name must not be null")
    @Size(min = 1, max = 100, message = "Ingredient name must be between(including) 1 and 100 characters")
    private String name;
    @NotNull(message = "Ingredient description name must not be null")
    @Size(min = 1, max = 1000, message = "Ingredient description must be between(including) 1 and 1000 characters")
    private String description;
    @NotNull(message = "Ingredient rating must not be null")
    @Min(value = 1, message = "Ingredient rating must be at least 1")
    @Max(value = 100, message = "Ingredient rating must be at most 100")
    private Integer rating;
    @NotNull(message = "Ingredient price in HRK must not be null")
    @PositiveOrZero(message = "Ingredient price in HRK must be a positive number or zero")
    private BigDecimal priceHrk;
}
