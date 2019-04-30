package ru.protopopova.service;

import ru.protopopova.model.Dish;

import java.util.List;

public interface DishService {

    List<Dish> get();
    Dish getById(int id);
    boolean delete(int id);
    Dish save(Dish dish);
}
