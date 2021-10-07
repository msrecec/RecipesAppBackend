package com.backend.recipes.repository.recipeItem;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class ShoppingListRepositoryImpl implements ShoppingListRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShoppingListRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM shopping_list_item WHERE shopping_list_id = ?", id);
        jdbcTemplate.update("DELETE FROM shopping_list WHERE id = ?", id);
    }
}
