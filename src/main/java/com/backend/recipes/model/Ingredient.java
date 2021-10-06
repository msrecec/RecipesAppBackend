package com.backend.recipes.model;

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
    @SequenceGenerator(name = "ingredient_sequence", sequenceName = "ingredient_sequence", allocationSize = 1)
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
    private BigDecimal totalPriceHrk;
    @Column(name = "price_eur")
    private BigDecimal totalPriceEur;
    @OneToMany(mappedBy = "ingredient")
    List<ShoppingListItem> shoppingListItems;
    @OneToMany(mappedBy = "ingredient")
    List<RecipeItem> recipeItems;
}
