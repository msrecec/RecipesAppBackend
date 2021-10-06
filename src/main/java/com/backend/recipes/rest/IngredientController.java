package com.backend.recipes.rest;

import com.backend.recipes.command.ingredient.IngredientSaveCommand;
import com.backend.recipes.command.ingredient.IngredientUpdateCommand;
import com.backend.recipes.dto.ingredient.IngredientDTO;
import com.backend.recipes.service.ingredient.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/ingredient")
public class IngredientController {

    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<IngredientDTO> getAllIngredients() {
        return ingredientService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable final Long id) {
        return ingredientService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @PostMapping
    public ResponseEntity<IngredientDTO> save(@Valid @RequestBody final IngredientSaveCommand command) {
        return ingredientService.save(command)
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
    public ResponseEntity<IngredientDTO> update(@Valid @RequestBody final IngredientUpdateCommand command) {
        return ingredientService.update(command)
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
        ingredientService.deleteById(id);
    }

}
