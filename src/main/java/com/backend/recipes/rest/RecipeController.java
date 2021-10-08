package com.backend.recipes.rest;

import com.backend.recipes.command.recipe.RecipeSaveCommand;
import com.backend.recipes.command.recipe.RecipeUpdateCommand;
import com.backend.recipes.dto.recipe.RecipeDTO;
import com.backend.recipes.dto.recipe.RecipeDTOPaginated;
import com.backend.recipes.service.recipe.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/recipe")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<RecipeDTO> getRecipe() {
        return recipeService.findAll();
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<RecipeDTOPaginated> getIngredientPaginated(@PathVariable final Integer page) {
        if(page < 0) {
            return ResponseEntity.badRequest().build();
        }

        return recipeService.findPaginated(page)
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

    @GetMapping("/id/{id}")
    public ResponseEntity<RecipeDTO> getIngredientById(@PathVariable final Long id) {
        return recipeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> save(@Valid @RequestBody final RecipeSaveCommand command) {
        return recipeService.save(command)
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

    @PutMapping
    public ResponseEntity<RecipeDTO> update(@Valid @RequestBody final RecipeUpdateCommand command) {
        return recipeService.update(command)
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable Long id) {
        recipeService.deleteById(id);
    }

}
