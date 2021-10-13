package com.backend.recipes.repository.user;

import com.backend.recipes.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryJpa extends JpaRepository<User, Long> {

    Optional<User> findOneByUsername(String username);

}
