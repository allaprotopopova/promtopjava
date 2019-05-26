package ru.protopopova.service;

import ru.protopopova.model.Menu;
import ru.protopopova.util.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {

    List<Menu> getByRestaurant(int restaurant_id);
    List<Menu> getByDate(LocalDate localDate);
    Menu getByRestaurantAndDate(int restaurantId, LocalDate date) throws NotFoundException;
    default List<Menu> getCurrent() {
        return getByDate(LocalDate.now());
    }

    void delete(int id);
    Menu save(Menu menu, int restaurantId);





}
