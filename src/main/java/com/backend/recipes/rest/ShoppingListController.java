package com.backend.recipes.rest;

import com.backend.recipes.command.recipe.RecipeSaveCommand;
import com.backend.recipes.command.recipe.RecipeUpdateCommand;
import com.backend.recipes.command.shoppingList.ShoppingListSaveCommand;
import com.backend.recipes.command.shoppingList.ShoppingListUpdateCommand;
import com.backend.recipes.dto.recipe.RecipeDTO;
import com.backend.recipes.dto.recipe.RecipeDTOPaginated;
import com.backend.recipes.dto.shoppingList.ShoppingListDTO;
import com.backend.recipes.dto.shoppingList.ShoppingListDTOPaginated;
import com.backend.recipes.service.recipe.RecipeService;
import com.backend.recipes.service.shoppingList.ShoppingListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/shopping-list")
@CrossOrigin(origins = "http://localhost:4200")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping
    public List<ShoppingListDTO> findAll() {
        return shoppingListService.findAll();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/page/{page}")
    public ResponseEntity<ShoppingListDTOPaginated> getIngredientPaginated(@PathVariable final Integer page) {
        if(page < 0) {
            return ResponseEntity.badRequest().build();
        }

        return shoppingListService.findPaginated(page)
                .map(
                        ingredientDTOPaginated -> ResponseEntity
                                .status(HttpStatus.OK)
                                .body(ingredientDTOPaginated)
                )
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .build()
                );

    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/id/{id}")
    public ResponseEntity<ShoppingListDTO> getIngredientById(@PathVariable final Long id) {
        return shoppingListService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<ShoppingListDTO> save(@Valid @RequestBody final ShoppingListSaveCommand command) {
        return shoppingListService.save(command)
                .map(
                        ingredientDTO -> ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(ingredientDTO)
                )
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping
    public ResponseEntity<ShoppingListDTO> update(@Valid @RequestBody final ShoppingListUpdateCommand command) {
        return shoppingListService.update(command)
                .map(productDTO -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(productDTO)
                )
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable Long id) {
        shoppingListService.deleteById(id);
    }
}
