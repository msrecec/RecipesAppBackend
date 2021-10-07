package com.backend.recipes.service.recipeItem;

import com.backend.recipes.command.recipeItem.RecipeItemCommand;
import com.backend.recipes.dto.recipeItem.RecipeItemDTO;
import com.backend.recipes.mapping.mapper.recipeItem.RecipeItemMapper;
import com.backend.recipes.model.recipeItem.RecipeItem;
import com.backend.recipes.repository.recipeItem.RecipeItemRepositoryJpa;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeItemServiceImpl implements RecipeItemService {

    private final RecipeItemRepositoryJpa recipeItemRepositoryJpa;
    private final RecipeItemMapper recipeItemMapper;

    public RecipeItemServiceImpl(RecipeItemRepositoryJpa recipeItemRepositoryJpa, RecipeItemMapper recipeItemMapper) {
        this.recipeItemRepositoryJpa = recipeItemRepositoryJpa;
        this.recipeItemMapper = recipeItemMapper;
    }

    @Override
    public Optional<RecipeItemDTO> update(RecipeItemCommand command) {
        Optional<RecipeItem> recipeItem = recipeItemRepositoryJpa.findById(command.getId());

        if(recipeItem.isEmpty()) {
            return Optional.empty();
        }

        recipeItem.get().setQuantity(command.getQuantity());

        recipeItem = Optional.of(recipeItemRepositoryJpa.save(recipeItem.get()));

        return recipeItem.map(recipeItemMapper::mapRecipeItemToDTO);
    }

    @Override
    public Optional<RecipeItemDTO> findById(Long id) {
        return recipeItemRepositoryJpa.findById(id).map(recipeItemMapper::mapRecipeItemToDTO);
    }

    @Override
    public List<RecipeItemDTO> findAll() {
        return recipeItemRepositoryJpa.findAll().stream().map(recipeItemMapper::mapRecipeItemToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        recipeItemRepositoryJpa.deleteById(id);
    }
}
