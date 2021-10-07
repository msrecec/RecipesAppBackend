package com.backend.recipes.rest;

import com.backend.recipes.model.shoppingListItem.ShoppingListItem;
import com.backend.recipes.service.shoppingListItem.ShoppingListItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/shopping-list-item")
public class ShoppingListItemController {

    private final ShoppingListItemService shoppingListItemService;

    public ShoppingListItemController(ShoppingListItemService shoppingListItemService) {
        this.shoppingListItemService = shoppingListItemService;
    }

    @GetMapping
    public List<ShoppingListItem> findAll() {
        return shoppingListItemService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<>

}
