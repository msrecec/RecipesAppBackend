package com.backend.recipes.repository.recipeItem;

import com.backend.recipes.model.recipeItem.RecipeItem;
import com.backend.recipes.model.shoppingListItem.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeItemRepositoryJpa extends JpaRepository<RecipeItem, Long> {
}
