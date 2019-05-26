package ru.protopopova.service;

import ru.protopopova.model.Dish;
import ru.protopopova.util.NotFoundException;

import java.util.List;

public interface DishService {

    List<Dish> get();
    Dish getById(int id) throws NotFoundException;
    void delete(int id) throws NotFoundException;
    Dish save(Dish dish);
}
