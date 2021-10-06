package com.backend.recipes.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "recipe", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "id", columnNames = "id")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Recipe {
    @SequenceGenerator(name = "recipe_sequence", sequenceName = "recipe_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_sequence")
    @Column(name = "id")
    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    private Date date;
    @Column(name = "total_price_hrk")
    private BigDecimal totalPriceHrk;
    @Column(name = "total_price_eur")
    private BigDecimal totalPriceEur;
    @OneToMany(mappedBy = "recipe")
    List<RecipeItem> recipeItems;
}
