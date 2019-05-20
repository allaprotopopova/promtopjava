package ru.protopopova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.protopopova.model.Menu;
import ru.protopopova.repository.CrudMenuRepository;
import ru.protopopova.util.EmptyMenuException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final CrudMenuRepository repository;

    @Autowired
    public MenuServiceImpl(CrudMenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Menu> getMenuByRestaurant(int restaurantId) {
        return repository.findByRestaurantId(restaurantId).orElse(new ArrayList<>());
    }

    @Override
    public List<Menu> getMenuByDate(LocalDate localDate) {
        return repository.findByCreated(localDate).orElse(new ArrayList<>());
    }

    @Override
    public Menu getByRestaurantAndDate(int restaurantId, LocalDate date) {
        return repository.findByCreatedAndRestaurantId(date, restaurantId).orElse(null);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id)!=0;
    }

    @Override
    public Menu save(Menu menu) {
        if (menu.getDishes()==null || menu.getDishes().isEmpty()) {
            throw new EmptyMenuException();
        }
        return repository.save(menu);
    }
}
