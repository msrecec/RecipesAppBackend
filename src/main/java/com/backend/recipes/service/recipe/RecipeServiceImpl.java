package com.backend.recipes.service.recipe;

import com.backend.recipes.command.recipe.RecipeSaveCommand;
import com.backend.recipes.command.recipe.RecipeUpdateCommand;
import com.backend.recipes.command.recipe.nested.RecipeItemNestedSaveCommand;
import com.backend.recipes.dto.recipe.RecipeDTO;
import com.backend.recipes.dto.recipe.RecipeDTOPaginated;
import com.backend.recipes.mapping.mapper.recipe.RecipeMapper;
import com.backend.recipes.model.hnb.Currency;
import com.backend.recipes.model.ingredient.Ingredient;
import com.backend.recipes.model.recipe.Recipe;
import com.backend.recipes.model.recipeItem.RecipeItem;
import com.backend.recipes.repository.hnbAPI.HnbRepository;
import com.backend.recipes.repository.ingredient.IngredientRepositoryJpa;
import com.backend.recipes.repository.recipe.RecipeRepository;
import com.backend.recipes.repository.recipe.RecipeRepositoryJpa;
import com.backend.recipes.repository.recipeItem.RecipeItemRepositoryJpa;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService{

    RecipeRepositoryJpa recipeRepositoryJpa;
    RecipeRepository recipeRepository;
    IngredientRepositoryJpa ingredientRepositoryJpa;
    RecipeItemRepositoryJpa recipeItemRepositoryJpa;
    HnbRepository hnbRepository;
    RecipeMapper recipeMapper;
    private static final Integer pageSize = 10;

    public RecipeServiceImpl(
            RecipeRepositoryJpa recipeRepositoryJpa,
            RecipeRepository recipeRepository,
            RecipeMapper recipeMapper,
            IngredientRepositoryJpa ingredientRepositoryJpa,
            RecipeItemRepositoryJpa recipeItemRepositoryJpa,
            HnbRepository hnbRepository
    ) {
        this.recipeRepositoryJpa = recipeRepositoryJpa;
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.ingredientRepositoryJpa = ingredientRepositoryJpa;
        this.recipeItemRepositoryJpa = recipeItemRepositoryJpa;
        this.hnbRepository = hnbRepository;
    }

    @Override
    public List<RecipeDTO> findAll() {
        return recipeRepositoryJpa.findAll().stream().map(recipeMapper::mapRecipeToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<RecipeDTO> findById(Long id) {
        return recipeRepositoryJpa.findById(id).map(recipeMapper::mapRecipeToDTO);
    }

    @Override
    public Optional<RecipeDTOPaginated> findPaginated(Integer page) {

        if(page < 0) {
            return Optional.empty();
        }

        PageRequest pageRequest = PageRequest.of(page, pageSize);

        List<Recipe> recipes = recipeRepositoryJpa.findAll(pageRequest).getContent();

        long totalPages = recipeRepositoryJpa.findAll(pageRequest).getTotalPages();

        return Optional.of(new RecipeDTOPaginated(recipes.stream().map(recipeMapper::mapRecipeToDTO).collect(Collectors.toList()), totalPages));
    }

    @Override
    @Transactional
    public Optional<RecipeDTO> save(RecipeSaveCommand command) {

        Recipe recipe = Recipe.builder()
                .name(command.getName())
                .shortDescription(command.getShortDescription())
                .description(command.getDescription())
                .date(new Date())
                .build();

        recipe = recipeRepositoryJpa.save(recipe);

        List<RecipeItemNestedSaveCommand> recipeItemNestedSaveCommands = command.getRecipeItems();
        List<RecipeItem> recipeItems = new ArrayList<>();

        for(RecipeItemNestedSaveCommand nestedSaveCommand : recipeItemNestedSaveCommands) {
            Optional<Ingredient> ingredient = ingredientRepositoryJpa.findById(nestedSaveCommand.getId());
            if(ingredient.isPresent()) {
                RecipeItem recipeItem = RecipeItem.builder().quantity(nestedSaveCommand.getQuantity()).ingredient(ingredient.get()).recipe(recipe).build();
                recipeItem = recipeItemRepositoryJpa.save(recipeItem);
                recipeItems.add(recipeItem);
            }
        }

        recipe.setRecipeItems(recipeItems);

        recipe.setTotalPriceHrk(recipeItems.stream().map(item -> item.getIngredient()
                .getPriceHrk().multiply(new BigDecimal(item.getQuantity())))
                .reduce(new BigDecimal(0), (a, b) -> a.add(b)));

        recipe.setTotalPriceEur(recipe.getTotalPriceHrk().divide(new BigDecimal(hnbRepository.findByCurrency(Currency.EUR).get().getSrednjiZaDevize().replace(",", ".")), 2, RoundingMode.HALF_UP));

        recipe = recipeRepositoryJpa.save(recipe);

        return Optional.of(recipeMapper.mapRecipeToDTO(recipe));
    }

    @Override
    public Optional<RecipeDTO> update(RecipeUpdateCommand command) {
        Optional<Recipe> recipe = recipeRepositoryJpa.findById(command.getId());

        if(recipe.isEmpty()) {
            return Optional.empty();
        }

        recipe.get().setName(command.getName());
        recipe.get().setShortDescription(command.getShortDescription());
        recipe.get().setDescription(command.getDescription());

        for(RecipeItemNestedSaveCommand nestedSaveCommand : command.getRecipeItems()) {
            boolean present = false;
            for(RecipeItem recipeItem : recipe.get().getRecipeItems()) {
                if(recipeItem.getIngredient().getId().equals(nestedSaveCommand.getId())) {
                    present = true;
                    recipeItem.setQuantity(nestedSaveCommand.getQuantity());
                    recipeItemRepositoryJpa.save(recipeItem);
                }
            }
            if(!present) {
                Optional<Ingredient> ingredient = ingredientRepositoryJpa.findById(nestedSaveCommand.getId());
                if(ingredient.isPresent()) {
                    RecipeItem recipeItem1 = RecipeItem.builder().quantity(nestedSaveCommand.getQuantity()).recipe(recipe.get()).ingredient(ingredient.get()).build();
                    recipeItem1 = recipeItemRepositoryJpa.save(recipeItem1);
                    recipe.get().getRecipeItems().add(recipeItem1);
                }
            }
        }

        for(RecipeItem recipeItem : recipe.get().getRecipeItems()) {
            boolean present = false;
            for(RecipeItemNestedSaveCommand nestedSaveCommand : command.getRecipeItems()) {
                if(recipeItem.getIngredient().getId().equals(nestedSaveCommand.getId())) {
                    present = true;
                }
            }
            if(!present) {
                recipeItemRepositoryJpa.deleteById(recipeItem.getId());
                recipe.get().getRecipeItems().remove(recipeItem);
            }
        }

        recipe.get().setTotalPriceHrk(recipe.get().getRecipeItems().stream().map(item -> item.getIngredient()
                        .getPriceHrk().multiply(new BigDecimal(item.getQuantity())))
                .reduce(new BigDecimal(0), (a, b) -> a.add(b)));

        recipe.get().setTotalPriceEur(recipe.get().getTotalPriceHrk().divide(new BigDecimal(hnbRepository.findByCurrency(Currency.EUR)
                .get().getSrednjiZaDevize().replace(",", ".")), 2, RoundingMode.HALF_UP));

        recipe = Optional.of(recipeRepositoryJpa.save(recipe.get()));

        return recipe.map(recipeMapper::mapRecipeToDTO);


    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        recipeRepository.deleteById(id);

    }
}
