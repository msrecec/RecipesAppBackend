package com.backend.recipes.mapping.mapper.user;

import com.backend.recipes.dto.user.UserDTO;
import com.backend.recipes.model.user.User;

public interface UserMapper {
    UserDTO mapUserToDTO(User user);
}
