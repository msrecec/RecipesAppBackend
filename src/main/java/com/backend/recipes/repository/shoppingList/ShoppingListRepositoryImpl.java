package com.backend.recipes.repository.shoppingList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class ShoppingListRepositoryImpl implements ShoppingListRepository {

    private JdbcTemplate jdbcTemplate;

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
