package com.backend.recipes.service.ingredient;

import com.backend.recipes.command.ingredient.IngredientSaveCommand;
import com.backend.recipes.command.ingredient.IngredientUpdateCommand;
import com.backend.recipes.dto.ingredient.IngredientDTO;
import com.backend.recipes.dto.ingredient.IngredientDTOPaginated;
import com.backend.recipes.mapping.mapper.ingredient.IngredientMapper;
import com.backend.recipes.model.hnb.Currency;
import com.backend.recipes.model.hnb.Hnb;
import com.backend.recipes.model.ingredient.Ingredient;
import com.backend.recipes.repository.hnbAPI.HnbRepository;
import com.backend.recipes.repository.ingredient.IngredientRepository;
import com.backend.recipes.repository.ingredient.IngredientRepositoryJpa;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepositoryJpa ingredientRepositoryJpa;
    private final IngredientRepository ingredientRepository;
    private final HnbRepository hnbRepository;
    private final IngredientMapper ingredientMapper;
    private static final Integer pageSize = 10;

    public IngredientServiceImpl(
            IngredientRepositoryJpa ingredientRepositoryJpa,
            HnbRepository hnbRepository,
            IngredientMapper ingredientMapper,
            IngredientRepository ingredientRepository
    ) {
        this.ingredientRepositoryJpa = ingredientRepositoryJpa;
        this.hnbRepository = hnbRepository;
        this.ingredientMapper = ingredientMapper;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @Transactional
    public Optional<IngredientDTO> save(IngredientSaveCommand ingredientSaveCommand) {

        Ingredient ingredient = Ingredient
                .builder()
                .name(ingredientSaveCommand.getName())
                .description(ingredientSaveCommand.getDescription())
                .date(new Date())
                .rating(ingredientSaveCommand.getRating())
                .priceHrk(ingredientSaveCommand.getPriceHrk())
                .build();

        Optional<Hnb> hnb = hnbRepository.findByCurrency(Currency.EUR);

        if(hnb.isEmpty()) {
            return Optional.empty();
        }

        ingredient.setPriceEur(ingredient.getPriceHrk().divide(new BigDecimal(hnb.get().getSrednjiZaDevize().replace(",", ".")), 2,RoundingMode.HALF_UP));

        ingredient = ingredientRepositoryJpa.save(ingredient);

        return Optional.of(ingredientMapper.mapIngredientToDTO(ingredient));

    }

    @Override
    public List<IngredientDTO> findAll() {
        return this.ingredientRepositoryJpa.findAll().stream().map(ingredientMapper::mapIngredientToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<IngredientDTOPaginated> findPaginated(Integer page) {

        if(page < 0 ) {
            return Optional.empty();
        }

        PageRequest pageRequest = PageRequest.of(page, pageSize);

        List<Ingredient> ingredients = ingredientRepositoryJpa.findAll(pageRequest).getContent();

        long totalPages = ingredientRepositoryJpa.findAll(pageRequest).getTotalPages();

        return Optional.of(new IngredientDTOPaginated(ingredients.stream().map(ingredientMapper::mapIngredientToDTO).collect(Collectors.toList()), totalPages));

    }

    @Override
    public Optional<IngredientDTO> findById(Long id) {
        return ingredientRepositoryJpa.findById(id).map(ingredientMapper::mapIngredientToDTO);
    }

    @Override
    @Transactional
    public Optional<IngredientDTO> update(IngredientUpdateCommand ingredientUpdateCommand) {

        Ingredient ingredient = Ingredient
                .builder()
                .id(ingredientUpdateCommand.getId())
                .name(ingredientUpdateCommand.getName())
                .description(ingredientUpdateCommand.getDescription())
                .date(new Date())
                .rating(ingredientUpdateCommand.getRating())
                .priceHrk(ingredientUpdateCommand.getPriceHrk())
                .build();

        Optional<Hnb> hnb = hnbRepository.findByCurrency(Currency.EUR);

        if(hnb.isEmpty()) {
            return Optional.empty();
        }

        ingredient.setPriceEur(ingredient.getPriceHrk().divide(new BigDecimal(hnb.get().getSrednjiZaDevize().replace(",", ".")), 2,RoundingMode.HALF_UP));

        ingredient = ingredientRepositoryJpa.save(ingredient);

        return Optional.of(ingredientMapper.mapIngredientToDTO(ingredient));

    }

    @Override
    public void deleteById(Long id) {
        ingredientRepository.deleteById(id);
    }
}
