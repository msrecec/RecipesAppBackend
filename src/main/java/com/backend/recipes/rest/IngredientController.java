package com.backend.recipes.rest;

import com.backend.recipes.command.ingredient.IngredientSaveCommand;
import com.backend.recipes.command.ingredient.IngredientUpdateCommand;
import com.backend.recipes.dto.ingredient.IngredientDTO;
import com.backend.recipes.dto.ingredient.IngredientDTOPaginated;
import com.backend.recipes.service.ingredient.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/ingredient")
@CrossOrigin(origins = "http://localhost:4200")
public class IngredientController {

    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping
    public List<IngredientDTO> getAllIngredients() {
        return ingredientService.findAll();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/page/{page}")
    public ResponseEntity<IngredientDTOPaginated> getIngredientPaginated(@PathVariable final Integer page) {
        if(page < 0) {
            return ResponseEntity.badRequest().build();
        }

        return ingredientService.findPaginated(page)
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
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable final Long id) {
        return ingredientService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
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

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
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

    @Secured({"ROLE_USER"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable Long id) {
        ingredientService.deleteById(id);
    }

}
