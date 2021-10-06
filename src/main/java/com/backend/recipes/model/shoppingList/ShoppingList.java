package com.backend.recipes.model.shoppingList;

import com.backend.recipes.model.shoppingListItem.ShoppingListItem;
import com.backend.recipes.model.ingredient.Ingredient;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shopping_list", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "id", columnNames = "id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ShoppingList {
    @SequenceGenerator(name = "shopping_list_sequence", sequenceName = "shopping_list_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_list_sequence")
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private Date date;
    @Column(name = "total_price_hrk")
    private BigDecimal totalPriceHrk;
    @Column(name = "total_price_eur")
    private BigDecimal totalPriceEur;
    @OneToMany(targetEntity = ShoppingListItem.class ,mappedBy = "shoppingList")
    private List<ShoppingListItem> shoppingListItems;
    @ManyToMany(targetEntity = Ingredient.class)
    @JoinTable(
            name="shopping_list_item",
            joinColumns = {@JoinColumn(name = "shopping_list_id")},
            inverseJoinColumns = {@JoinColumn(name = "ingredient_id")}
    )
    private List<Ingredient> ingredients;
}
