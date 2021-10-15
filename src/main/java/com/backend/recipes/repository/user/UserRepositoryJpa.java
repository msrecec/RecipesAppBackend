package com.backend.recipes.repository.user;

import com.backend.recipes.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJpa extends JpaRepository<User, Long> {

    Optional<User> findOneByUsername(String username);

}
