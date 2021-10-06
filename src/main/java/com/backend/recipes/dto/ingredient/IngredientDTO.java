package com.backend.recipes.dto.ingredient;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class IngredientDTO {
    private Long id;
    private String name;
    private String description;
    private Date date;
    private Integer rating;
    private BigDecimal priceHrk;
    private BigDecimal priceEur;
}
