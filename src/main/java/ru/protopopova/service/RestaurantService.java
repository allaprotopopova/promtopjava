package ru.protopopova.service;

import ru.protopopova.model.Restaurant;
import ru.protopopova.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RestaurantService {

    List<Restaurant> get();
    Restaurant getById(int id);
    boolean delete(int id);
    Restaurant save(Restaurant restaurant);






}
