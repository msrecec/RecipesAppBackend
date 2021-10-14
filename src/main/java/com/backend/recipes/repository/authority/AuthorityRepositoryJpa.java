package com.backend.recipes.repository.authority;

import com.backend.recipes.model.authority.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepositoryJpa extends JpaRepository<Authority, Long> {
    List<Authority> findByName(String name);
}
