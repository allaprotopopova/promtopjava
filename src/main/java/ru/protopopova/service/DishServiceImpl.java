package ru.protopopova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.protopopova.model.Dish;
import ru.protopopova.repository.CrudDishRepository;
import ru.protopopova.util.NotFoundException;

import java.util.List;

import static ru.protopopova.util.ValidationUtil.checkNotFound;
import static ru.protopopova.util.ValidationUtil.checkNotFoundWithId;

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
    public Dish getById(int id) throws NotFoundException {
        return  checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Override
    public void delete(int id) {
                checkNotFoundWithId(repository.delete(id)!=0, id);
    }

    @Override
    public Dish save(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish);
    }
}
