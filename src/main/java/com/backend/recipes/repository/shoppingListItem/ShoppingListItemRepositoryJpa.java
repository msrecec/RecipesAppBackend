package com.backend.recipes.repository.shoppingListItem;

import com.backend.recipes.model.ingredient.Ingredient;
import com.backend.recipes.model.shoppingListItem.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListItemRepositoryJpa extends JpaRepository<ShoppingListItem, Long> {
}
