package com.backend.recipes.repository.hnbAPI;

import com.backend.recipes.model.hnb.Currency;
import com.backend.recipes.model.hnb.Hnb;

import java.util.Optional;

public interface HnbRepository {
    Optional<Hnb> findByCurrency(Currency currency);
}
