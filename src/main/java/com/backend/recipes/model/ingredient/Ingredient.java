package com.backend.recipes.model.ingredient;

import com.backend.recipes.model.recipe.Recipe;
import com.backend.recipes.model.recipeItem.RecipeItem;
import com.backend.recipes.model.shoppingList.ShoppingList;
import com.backend.recipes.model.shoppingListItem.ShoppingListItem;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ingredient", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "id", columnNames = "id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Ingredient {
    @SequenceGenerator(name = "ingredient_sequence", sequenceName = "ingredient_sequence", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_sequence")
    @Column(name = "id")
    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    private Date date;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "price_hrk")
    private BigDecimal priceHrk;
    @Column(name = "price_eur")
    private BigDecimal priceEur;
    @OneToMany(targetEntity = ShoppingListItem.class ,mappedBy = "ingredient")
    List<ShoppingListItem> shoppingListItems;
    @OneToMany(targetEntity = RecipeItem.class ,mappedBy = "ingredient")
    List<RecipeItem> recipeItems;
    @ManyToMany(targetEntity = ShoppingList.class, mappedBy = "ingredients")
    private List<ShoppingList> shoppingLists;
    @ManyToMany(targetEntity = Recipe.class, mappedBy = "ingredients")
    private List<Recipe> recipes;
}
