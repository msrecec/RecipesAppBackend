package com.backend.recipes.rest;

import com.backend.recipes.command.recipeItem.RecipeItemCommand;
import com.backend.recipes.command.shoppingListItem.ShoppingListItemCommand;
import com.backend.recipes.dto.recipeItem.RecipeItemDTO;
import com.backend.recipes.dto.shoppingListItem.ShoppingListItemDTO;
import com.backend.recipes.service.recipeItem.RecipeItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/recipe-item")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeItemController {

    private RecipeItemService recipeItemService;

    public RecipeItemController(RecipeItemService recipeItemService) {
        this.recipeItemService = recipeItemService;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping
    public List<RecipeItemDTO> findAll() {
        return recipeItemService.findAll();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/id/{id}")
    public ResponseEntity<RecipeItemDTO> findById(@PathVariable final Long id) {
        return recipeItemService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        ()->ResponseEntity.notFound().build()
                );
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping
    public ResponseEntity<RecipeItemDTO> update(@Valid @RequestBody final RecipeItemCommand command) {
        return recipeItemService.update(command)
                .map(shoppingListItemDTO -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(shoppingListItemDTO)
                )
                .orElseGet(
                        ()->ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable Long id) {
        recipeItemService.deleteById(id);
    }

}
