package ru.protopopova.service;

import ru.protopopova.model.Restaurant;
import ru.protopopova.util.NotFoundException;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> get();
    Restaurant getById(int id) throws NotFoundException;
    void delete(int id);
    Restaurant save(Restaurant restaurant);






}
