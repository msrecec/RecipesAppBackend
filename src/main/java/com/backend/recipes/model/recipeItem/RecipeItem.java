package com.backend.recipes.model.recipeItem;

import com.backend.recipes.model.ingredient.Ingredient;
import com.backend.recipes.model.recipe.Recipe;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "recipe_item", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "id", columnNames = "id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RecipeItem {
    @SequenceGenerator(name = "recipe_item_sequence", sequenceName = "recipe_item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_item_sequence")
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "quantity")
    private Integer quantity;
    @ManyToOne
    private Recipe recipe;
    @ManyToOne
    private Ingredient ingredient;
}
