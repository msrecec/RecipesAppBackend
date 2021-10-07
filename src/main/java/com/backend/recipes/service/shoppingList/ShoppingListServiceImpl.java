package com.backend.recipes.service.shoppingList;

import com.backend.recipes.command.recipe.RecipeSaveCommand;
import com.backend.recipes.command.recipe.RecipeUpdateCommand;
import com.backend.recipes.command.shoppingList.ShoppingListSaveCommand;
import com.backend.recipes.command.shoppingList.ShoppingListUpdateCommand;
import com.backend.recipes.command.shoppingList.nested.ShoppingListItemNestedSaveCommand;
import com.backend.recipes.dto.shoppingList.ShoppingListDTO;
import com.backend.recipes.dto.shoppingList.ShoppingListDTOPaginated;
import com.backend.recipes.mapping.mapper.shoppingList.ShoppingListMapper;
import com.backend.recipes.model.hnb.Currency;
import com.backend.recipes.model.ingredient.Ingredient;
import com.backend.recipes.model.shoppingList.ShoppingList;
import com.backend.recipes.model.shoppingListItem.ShoppingListItem;
import com.backend.recipes.repository.hnbAPI.HnbRepository;
import com.backend.recipes.repository.ingredient.IngredientRepositoryJpa;
import com.backend.recipes.repository.recipeItem.ShoppingListRepository;
import com.backend.recipes.repository.shoppingList.ShoppingListRepositoryJpa;
import com.backend.recipes.repository.shoppingListItem.ShoppingListItemRepositoryJpa;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShoppingListServiceImpl implements ShoppingListService {

    private final ShoppingListRepositoryJpa shoppingListRepositoryJpa;
    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListItemRepositoryJpa shoppingListItemRepositoryJpa;
    private final IngredientRepositoryJpa ingredientRepositoryJpa;
    private final HnbRepository hnbRepository;
    private final ShoppingListMapper shoppingListMapper;
    private static final Integer pageSize = 10;

    public ShoppingListServiceImpl(
            ShoppingListRepositoryJpa shoppingListRepositoryJpa,
            ShoppingListRepository shoppingListRepository,
            ShoppingListMapper shoppingListMapper,
            IngredientRepositoryJpa ingredientRepositoryJpa,
            ShoppingListItemRepositoryJpa shoppingListItemRepositoryJpa,
            HnbRepository hnbRepository
    ) {
        this.shoppingListRepositoryJpa = shoppingListRepositoryJpa;
        this.shoppingListRepository = shoppingListRepository;
        this.shoppingListMapper = shoppingListMapper;
        this.ingredientRepositoryJpa = ingredientRepositoryJpa;
        this.shoppingListItemRepositoryJpa = shoppingListItemRepositoryJpa;
        this.hnbRepository = hnbRepository;
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
    public Optional<ShoppingListDTO> save(ShoppingListSaveCommand command) {

        ShoppingList shoppingList = ShoppingList.builder()
                .name(command.getName())
                .date(command.getDate())
                .build();

        shoppingList = shoppingListRepositoryJpa.save(shoppingList);

        List<ShoppingListItemNestedSaveCommand> shoppingListItemNestedSaveCommands = command.getShoppingListItems();
        List<ShoppingListItem> shoppingListItems = new ArrayList<>();

        for(ShoppingListItemNestedSaveCommand nestedSaveCommand : shoppingListItemNestedSaveCommands) {
            Optional<Ingredient> ingredient = ingredientRepositoryJpa.findById(nestedSaveCommand.getId());
            if(ingredient.isPresent()) {
                ShoppingListItem shoppingListItem = ShoppingListItem
                        .builder()
                        .quantity(nestedSaveCommand.getQuantity())
                        .ingredient(ingredient.get())
                        .shoppingList(shoppingList)
                        .build();
                shoppingListItem = shoppingListItemRepositoryJpa.save(shoppingListItem);
                shoppingListItems.add(shoppingListItem);
            }
        }

        shoppingList.setShoppingListItems(shoppingListItems);

        shoppingList.setTotalPriceHrk(shoppingListItems.stream()
                .map(item -> item.getIngredient().getPriceHrk()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(new BigDecimal(0), (a,b) -> a.add(b)));

        shoppingList.setTotalPriceEur(shoppingList.getTotalPriceHrk()
                .divide(new BigDecimal(hnbRepository.findByCurrency(Currency.EUR).get()
                        .getSrednjiZaDevize().replace(",", ".")), 2, RoundingMode.HALF_UP));

        shoppingList = shoppingListRepositoryJpa.save(shoppingList);

        return Optional.of(shoppingListMapper.mapShoppingListToDTO(shoppingList));

    }

    @Override
    public Optional<ShoppingListDTO> update(ShoppingListUpdateCommand command) {
        Optional<ShoppingList> shoppingList = shoppingListRepositoryJpa.findById(command.getId());

        if(shoppingList.isEmpty()) {
            return Optional.empty();
        }

        shoppingList.get().setName(command.getName());
        shoppingList.get().setDate(command.getDate());

        for(ShoppingListItem shoppingListItem : shoppingList.get().getShoppingListItems()) {
            boolean present = false;
            for(ShoppingListItemNestedSaveCommand nestedSaveCommand : command.getShoppingListItems()) {
                if(shoppingListItem.getIngredient().getId().equals(nestedSaveCommand.getId())) {
                    present = true;
                }
            }
            if(!present) {
                shoppingListItemRepositoryJpa.deleteById(shoppingListItem.getId());
            }
        }

        for(ShoppingListItemNestedSaveCommand nestedSaveCommand : command.getShoppingListItems()) {
            boolean present = false;
            for(ShoppingListItem shoppingListItem : shoppingList.get().getShoppingListItems()) {
                if(shoppingListItem.getIngredient().getId().equals(nestedSaveCommand.getId())) {
                    present = true;
                    shoppingListItem.setQuantity(nestedSaveCommand.getQuantity());
                    shoppingListItemRepositoryJpa.save(shoppingListItem);
                }
            }
            if(!present) {
                Optional<Ingredient> ingredient = ingredientRepositoryJpa.findById(nestedSaveCommand.getId());
                if(ingredient.isPresent()) {
                    ShoppingListItem shoppingListItem = ShoppingListItem
                            .builder()
                            .quantity(nestedSaveCommand.getQuantity())
                            .shoppingList(shoppingList.get())
                            .ingredient(ingredient.get())
                            .build();
                    shoppingListItem = shoppingListItemRepositoryJpa.save(shoppingListItem);
                    shoppingList.get().getShoppingListItems().add(shoppingListItem);
                }
            }
        }

        shoppingList.get().setTotalPriceHrk(shoppingList.get().getShoppingListItems().stream().map(item -> item.getIngredient()
                        .getPriceHrk().multiply(new BigDecimal(item.getQuantity())))
                .reduce(new BigDecimal(0), (a, b) -> a.add(b)));

        shoppingList.get().setTotalPriceEur(shoppingList.get().getTotalPriceHrk().divide(new BigDecimal(hnbRepository.findByCurrency(Currency.EUR)
                .get().getSrednjiZaDevize().replace(",", ".")), 2, RoundingMode.HALF_UP));

        shoppingList = Optional.of(shoppingListRepositoryJpa.save(shoppingList.get()));

        return shoppingList.map(shoppingListMapper::mapShoppingListToDTO);

    }

    @Override
    public void deleteById(Long id) {
        shoppingListRepository.deleteById(id);
    }
}
