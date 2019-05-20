package ru.protopopova.service;

import ru.protopopova.model.Menu;
import ru.protopopova.model.Restaurant;
import ru.protopopova.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MenuService {

    List<Menu> getMenuByRestaurant(int restaurant_id);
    List<Menu> getMenuByDate(LocalDate localDate);
    Menu getByRestaurantAndDate(int restaurantId, LocalDate date);
    default List<Menu> getCurrentMenu() {
        return getMenuByDate(LocalDate.now());
    }

    boolean delete(int id);
    Menu save(Menu vote);





}
