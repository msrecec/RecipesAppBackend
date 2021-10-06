package com.backend.recipes.mapping.mapper.ingredient;

import com.backend.recipes.dto.ingredient.IngredientDTO;
import com.backend.recipes.dto.ingredient.IngredientDTOPaginated;
import com.backend.recipes.model.ingredient.Ingredient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public IngredientDTO mapIngredientToDTO(Ingredient ingredient) {
        return new IngredientDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getDescription(),
                ingredient.getDate(),
                ingredient.getRating(),
                ingredient.getPriceHrk(),
                ingredient.getPriceEur()
        );
    }
}
