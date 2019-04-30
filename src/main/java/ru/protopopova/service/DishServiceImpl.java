package ru.protopopova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.protopopova.model.Dish;
import ru.protopopova.repository.CrudDishRepository;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final CrudDishRepository repository;

    @Autowired
    public DishServiceImpl(CrudDishRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Dish> get() {
        return repository.findAll();
    }

    @Override
    public Dish getById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id)!=0;
    }

    @Override
    public Dish save(Dish dish) {
        return repository.save(dish);
    }
}
