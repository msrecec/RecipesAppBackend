package com.backend.recipes.dto.ingredient;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class IngredientDTOPaginated {
    List<IngredientDTO> ingredientDTO;
    long totalPages;
}
