package com.backend.recipes.dto.recipe;

import com.backend.recipes.dto.recipeItem.RecipeItemDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RecipeDTO {
    private Long id;
    private String name;
    private String shortDescription;
    private String description;
    private Date date;
    private BigDecimal totalPriceHrk;
    private BigDecimal totalPriceEur;
    private List<RecipeItemDTO> recipeItemDTO;
}
