package ru.protopopova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.protopopova.model.Menu;
import ru.protopopova.model.Restaurant;
import ru.protopopova.repository.CrudMenuRepository;
import ru.protopopova.repository.CrudRestaurantRepository;
import ru.protopopova.util.EmptyMenuException;
import ru.protopopova.util.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.protopopova.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuServiceImpl implements MenuService {

    private final CrudMenuRepository repository;
    private final CrudRestaurantRepository restaurantRepository;

    @Autowired
    public MenuServiceImpl(CrudMenuRepository repository, CrudRestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    @Cacheable("menus")
    public List<Menu> getByRestaurant(int restaurantId) {
        return repository.findByRestaurantId(restaurantId)
                .orElse(new ArrayList<>())
                .stream()
                .sorted(Comparator.comparing(Menu::getCreated))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("menus")
    public List<Menu> getByDate(LocalDate localDate) {
        Assert.notNull(localDate, "date must not be null");

        return repository.findByCreated(localDate).orElse(new ArrayList<>())
                .stream()
                .sorted(Comparator.comparing(Menu::getCreated))
                .collect(Collectors.toList());
    }

    @Override
    public Menu getByRestaurantAndDate(int restaurantId, LocalDate date) throws NotFoundException {
        Assert.notNull(date, "date must not be null");
        return checkNotFoundWithId(repository.findByCreatedAndRestaurantId(date, restaurantId).orElse(null), restaurantId);
    }

    @Override
    @CacheEvict("menus")
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    @CacheEvict("menus")
    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        menu.setRestaurant(restaurant);
        if (menu.getDishes() == null || menu.getDishes().isEmpty()) {
            throw new EmptyMenuException();
        }
        return repository.save(menu);
    }
}
