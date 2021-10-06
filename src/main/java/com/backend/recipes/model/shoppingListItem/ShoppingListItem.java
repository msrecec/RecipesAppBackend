package com.backend.recipes.model.shoppingListItem;

import com.backend.recipes.model.ingredient.Ingredient;
import com.backend.recipes.model.shoppingList.ShoppingList;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "shopping_list_item", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "id", columnNames = "id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ShoppingListItem {
    @SequenceGenerator(name = "shopping_list_item_sequence", sequenceName = "shopping_list_item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_list_item_sequence")
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "quantity")
    private Integer quantity;
    @ManyToOne
    private Ingredient ingredient;
    @ManyToOne
    private ShoppingList shoppingList;
}
