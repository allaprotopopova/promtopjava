package ru.protopopova.service;


import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.Assert;
import ru.protopopova.ApplicationConfig;
import ru.protopopova.DishesData;
import ru.protopopova.model.Dish;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.protopopova.DishesData.*;


public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    DishService service;

    @Test
    public void get() {
        assertMatch(service.get(), DishesData.DISHES);
    }

    @Test
    public void getById() {
        assertMatch(service.getById(6), DISH6);
    }
    @Test
    public void getByIdNotExist() {
        Assert.isNull(service.getById(99), "Exception when getByIdNotExist");
    }

    @Test
    public void delete() {
        service.delete(6);
        List<Dish> copyList = new ArrayList<>(DISHES);
        copyList.remove(DISH6);
        assertMatch(service.get(), copyList);
    }

    @Test
    public void deleteNotExist() {
        service.delete(99);
        List<Dish> copyList = new ArrayList<>(DISHES);
        assertMatch(service.get(), copyList);
    }

    @Test
    public void save() {
        Dish newDish = getCreated();
        Dish created = service.save(newDish);
        newDish.setId(created.getId());
        assertMatch(created, newDish);
    }

    @Test
    public void update() {
        Dish dish = service.getById(6);
        dish.setName("New Name");
        Dish updated = service.save(dish);
        assertMatch(updated, dish);
    }
}
