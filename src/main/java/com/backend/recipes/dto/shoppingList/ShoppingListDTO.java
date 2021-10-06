package com.backend.recipes.dto.shoppingList;

import com.backend.recipes.dto.shoppingListItem.ShoppingListItemDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ShoppingListDTO {
    private Long id;
    private String name;
    private Date date;
    private BigDecimal totalPriceHrk;
    private BigDecimal totalPriceEur;
    private List<ShoppingListItemDTO> shoppingListItemDTO;
}
