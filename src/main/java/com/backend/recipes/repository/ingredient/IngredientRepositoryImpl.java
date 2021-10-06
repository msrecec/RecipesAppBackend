package com.backend.recipes.repository.ingredient;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class IngredientRepositoryImpl implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    public IngredientRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM shopping_list_item WHERE ingredient_id = ?", id);
        jdbcTemplate.update("DELETE FROM recipe_item WHERE ingredient_id = ?", id);
        jdbcTemplate.update("DELETE FROM ingredient WHERE id = ?", id);
    }
}
