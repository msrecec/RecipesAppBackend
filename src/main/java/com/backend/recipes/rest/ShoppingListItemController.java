package com.backend.recipes.rest;

import com.backend.recipes.command.shoppingListItem.ShoppingListItemCommand;
import com.backend.recipes.dto.shoppingListItem.ShoppingListItemDTO;
import com.backend.recipes.service.shoppingListItem.ShoppingListItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/shopping-list-item")
@CrossOrigin(origins = "http://localhost:4200")
public class ShoppingListItemController {

    private final ShoppingListItemService shoppingListItemService;

    public ShoppingListItemController(ShoppingListItemService shoppingListItemService) {
        this.shoppingListItemService = shoppingListItemService;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping
    public List<ShoppingListItemDTO> findAll() {
        return shoppingListItemService.findAll();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/id/{id}")
    public ResponseEntity<ShoppingListItemDTO> findById(@PathVariable final Long id) {
        return shoppingListItemService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        ()->ResponseEntity.notFound().build()
                );
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping
    public ResponseEntity<ShoppingListItemDTO> update(@Valid @RequestBody final ShoppingListItemCommand command) {
        return shoppingListItemService.update(command)
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
        shoppingListItemService.deleteById(id);
    }


}
