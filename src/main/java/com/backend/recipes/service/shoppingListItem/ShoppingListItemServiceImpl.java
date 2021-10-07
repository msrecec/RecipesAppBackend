package com.backend.recipes.service.shoppingListItem;

import com.backend.recipes.command.shoppingListItem.ShoppingListItemCommand;
import com.backend.recipes.model.shoppingListItem.ShoppingListItem;
import com.backend.recipes.repository.shoppingListItem.ShoppingListItemRepositoryJpa;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListItemServiceImpl implements ShoppingListItemService{

    private final ShoppingListItemRepositoryJpa shoppingListItemRepositoryJpa;

    public ShoppingListItemServiceImpl(ShoppingListItemRepositoryJpa shoppingListItemRepositoryJpa) {
        this.shoppingListItemRepositoryJpa = shoppingListItemRepositoryJpa;
    }

    @Override
    public Optional<ShoppingListItem> update(ShoppingListItemCommand command) {
        Optional<ShoppingListItem> shoppingListItem = shoppingListItemRepositoryJpa.findById(command.getId());

        if(shoppingListItem.isEmpty()) {
            return shoppingListItem;
        }

        shoppingListItem.get().setQuantity(command.getQuantity());

        shoppingListItem = Optional.of(shoppingListItemRepositoryJpa.save(shoppingListItem.get()));

        return shoppingListItem;
    }

    @Override
    public Optional<ShoppingListItem> findById(Long id) {
        return shoppingListItemRepositoryJpa.findById(id);
    }

    @Override
    public List<ShoppingListItem> findAll() {
        return shoppingListItemRepositoryJpa.findAll();
    }
}
