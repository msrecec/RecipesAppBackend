package com.backend.recipes.repository.recipe;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RecipeRepositoryImpl implements RecipeRepository {

    private JdbcTemplate jdbcTemplate;

    public RecipeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM recipe_item WHERE recipe_id = ?", id);
        jdbcTemplate.update("DELETE FROM recipe WHERE id = ?", id);
    }
}
