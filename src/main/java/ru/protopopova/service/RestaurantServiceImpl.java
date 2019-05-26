package ru.protopopova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.protopopova.model.Restaurant;
import ru.protopopova.repository.CrudRestaurantRepository;
import ru.protopopova.util.NotFoundException;

import java.util.List;

import static ru.protopopova.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final CrudRestaurantRepository repository;

    @Autowired
    public RestaurantServiceImpl(CrudRestaurantRepository repository) {
        this.repository = repository;
       }


    @Override
    public List<Restaurant> get() {
        return repository.findAll();
    }

    @Override
    public Restaurant getById(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id)!=0, id);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }


}
