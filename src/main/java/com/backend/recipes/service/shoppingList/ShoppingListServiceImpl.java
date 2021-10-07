package com.backend.recipes.service.shoppingList;

import com.backend.recipes.command.recipe.RecipeSaveCommand;
import com.backend.recipes.command.recipe.RecipeUpdateCommand;
import com.backend.recipes.dto.shoppingList.ShoppingListDTO;
import com.backend.recipes.dto.shoppingList.ShoppingListDTOPaginated;
import com.backend.recipes.mapping.mapper.shoppingList.ShoppingListMapper;
import com.backend.recipes.model.shoppingList.ShoppingList;
import com.backend.recipes.repository.shoppingList.ShoppingListRepositoryJpa;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingListServiceImpl implements ShoppingListService {

    private final ShoppingListRepositoryJpa shoppingListRepositoryJpa;
    private final ShoppingListMapper shoppingListMapper;
    private static final Integer pageSize = 10;

    public ShoppingListServiceImpl(ShoppingListRepositoryJpa shoppingListRepositoryJpa, ShoppingListMapper shoppingListMapper) {
        this.shoppingListRepositoryJpa = shoppingListRepositoryJpa;
        this.shoppingListMapper = shoppingListMapper;
    }

    @Override
    public List<ShoppingListDTO> findAll() {
        return shoppingListRepositoryJpa.findAll().stream().map(shoppingListMapper::mapShoppingListToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ShoppingListDTO> findById(Long id) {
        return shoppingListRepositoryJpa.findById(id).map(shoppingListMapper::mapShoppingListToDTO);
    }

    @Override
    public Optional<ShoppingListDTOPaginated> findPaginated(Integer page) {

        if(page < 0) {
            return Optional.empty();
        }

        PageRequest pageRequest = PageRequest.of(page, pageSize);

        List<ShoppingList> shoppingLists = shoppingListRepositoryJpa.findAll(pageRequest).getContent();

        long totalPages = shoppingListRepositoryJpa.findAll(pageRequest).getTotalPages();

        long totalElements = shoppingListRepositoryJpa.findAll(pageRequest).getTotalElements();

        return Optional.of(new ShoppingListDTOPaginated(shoppingLists.stream().map(shoppingListMapper::mapShoppingListToDTO).collect(Collectors.toList()), totalPages, totalElements));

    }

    @Override
    public Optional<ShoppingListDTO> save(RecipeSaveCommand command) {
        return Optional.empty();
    }

    @Override
    public Optional<ShoppingListDTO> update(RecipeUpdateCommand command) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
}
