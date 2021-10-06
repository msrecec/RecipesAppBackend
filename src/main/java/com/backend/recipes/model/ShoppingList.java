package com.backend.recipes.model;

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
    @OneToMany(mappedBy = "shoppingList")
    private List<ShoppingListItem> shoppingListItems;
}
