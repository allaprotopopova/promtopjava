package ru.protopopova.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.protopopova.model.Restaurant;
import ru.protopopova.util.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.protopopova.RestarauntTestData.*;


class RestaurantServiceImplTest extends AbstractServiceTest {

    Logger logger = LoggerFactory.getLogger(RestaurantServiceImplTest.class);

    @Autowired
    RestaurantService service;

    @Test
    public void get() {
        assertMatch(service.get(), List.of(RESTAURANT_1, RESTAURANT_2, RESTAURANT_3));
    };

    @Test
    public void getById() {
        assertMatch(service.getById(1), RESTAURANT_1);

    };


    @Test
    public void getByIdNotExist() {
        assertThrows(NotFoundException.class, ()->service.getById(7));

    };

    @Test
    void delete() {
        service.delete(1);
        assertMatch(service.get(), List.of(RESTAURANT_2, RESTAURANT_3));
    }

    @Test
    void save() {
         Restaurant newRest = getCreated();
         Restaurant saved = service.save(newRest);
         newRest.setId(saved.getId());
         assertMatch(saved, newRest);
    }

    @Test
    void update() {
        Restaurant updateRest = new Restaurant(RESTAURANT_1);
        updateRest.setName("New name");
        Restaurant saved = service.save(updateRest);
        updateRest.setId(saved.getId());
        assertMatch(saved, updateRest);
    }
}