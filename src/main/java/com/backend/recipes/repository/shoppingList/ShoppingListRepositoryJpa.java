package com.backend.recipes.repository.shoppingList;

import com.backend.recipes.model.shoppingList.ShoppingList;
import com.backend.recipes.model.shoppingListItem.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepositoryJpa extends JpaRepository<ShoppingList, Long> {
}
