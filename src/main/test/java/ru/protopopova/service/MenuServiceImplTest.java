package ru.protopopova.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.protopopova.model.Menu;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.protopopova.MenuData.*;

public class MenuServiceImplTest extends AbstractServiceTest {

    @Autowired
    MenuService service;

    @Test
    void getMenuByRestaurant() {
        assertMatch(service.getByRestaurant(1), List.of(MENU_1, MENU_2, MENU_5));
    }

    @Test
    void getMenuByRestaurantNotExist() {
        assertTrue(service.getByRestaurant(45).isEmpty());
    }

    @Test
    void getMenuByDate() {
        assertMatch(service.getByDate(LocalDate.of(2019, 4, 30)), List.of(MENU_1, MENU_3));
    }

    @Test
    void getByRestaurantAndDate() {
        assertMatch(service.getByRestaurantAndDate(1, LocalDate.of(2019, 4, 30)), MENU_1);
    }

    @Test
    void delete() {
        service.delete(21);
        assertMatch(service.getByRestaurant(1), List.of(MENU_2, MENU_5));
    }

    @Test
    void save() {
        Menu newMenu = getCreated();
        Menu savedMenu = service.save(newMenu, 2);
        newMenu.setId(savedMenu.getId());
        assertMatch(savedMenu, newMenu);

    }
}