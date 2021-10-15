package com.backend.recipes.service.user;

import com.backend.recipes.dto.user.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDTO> findById(Long id);
    List<UserDTO> findAll();
}
