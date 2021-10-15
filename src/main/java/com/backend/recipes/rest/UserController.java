package com.backend.recipes.rest;


import com.backend.recipes.dto.user.UserDTO;
import com.backend.recipes.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    ResponseEntity<UserDTO> findById(@PathVariable(name = "id") final long id) {
        return this.userService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    List<UserDTO> findAll() {
        return this.userService.findAll();
    }
}
