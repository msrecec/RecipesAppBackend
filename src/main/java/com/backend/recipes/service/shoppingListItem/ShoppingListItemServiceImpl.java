package com.backend.recipes.service.shoppingListItem;

import com.backend.recipes.command.shoppingListItem.ShoppingListItemCommand;
import com.backend.recipes.dto.shoppingListItem.ShoppingListItemDTO;
import com.backend.recipes.mapping.mapper.shoppingListItem.ShoppingListItemMapper;
import com.backend.recipes.model.shoppingListItem.ShoppingListItem;
import com.backend.recipes.repository.shoppingListItem.ShoppingListItemRepositoryJpa;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingListItemServiceImpl implements ShoppingListItemService{

    private final ShoppingListItemRepositoryJpa shoppingListItemRepositoryJpa;
    private final ShoppingListItemMapper shoppingListItemMapper;

    public ShoppingListItemServiceImpl(ShoppingListItemRepositoryJpa shoppingListItemRepositoryJpa, ShoppingListItemMapper shoppingListItemMapper) {
        this.shoppingListItemRepositoryJpa = shoppingListItemRepositoryJpa;
        this.shoppingListItemMapper = shoppingListItemMapper;
    }

    @Override
    public Optional<ShoppingListItemDTO> update(ShoppingListItemCommand command) {
        Optional<ShoppingListItem> shoppingListItem = shoppingListItemRepositoryJpa.findById(command.getId());

        if(shoppingListItem.isEmpty()) {
            return Optional.empty();
        }

        shoppingListItem.get().setQuantity(command.getQuantity());

        shoppingListItem = Optional.of(shoppingListItemRepositoryJpa.save(shoppingListItem.get()));

        return shoppingListItem.map(shoppingListItemMapper::mapShoppingListItemToDTO);
    }

    @Override
    public Optional<ShoppingListItemDTO> findById(Long id) {
        return shoppingListItemRepositoryJpa.findById(id).map(shoppingListItemMapper::mapShoppingListItemToDTO);
    }

    @Override
    public List<ShoppingListItemDTO> findAll() {
        return shoppingListItemRepositoryJpa.findAll().stream().map(shoppingListItemMapper::mapShoppingListItemToDTO).collect(Collectors.toList());
    }
}
