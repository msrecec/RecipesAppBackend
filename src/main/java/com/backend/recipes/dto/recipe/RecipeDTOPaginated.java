package com.backend.recipes.dto.recipe;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RecipeDTOPaginated {
    List<RecipeDTO> recipeDTO;
    long totalPages;
    long totalElements;
}
