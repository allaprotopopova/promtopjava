package ru.protopopova.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.protopopova.model.Role;
import ru.protopopova.model.User;
import ru.protopopova.util.NotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.protopopova.UserTestData.*;

class UserServiceImplTest extends AbstractServiceTest {

    @Autowired
    UserService service;

    @Test
    void create() {
        User newUser = getCreated();
        User created = service.create(newUser);
        newUser.setId(created.getId());
        assertMatch(service.getAll(), List.of(USER_1, USER_2, USER_3, ADMIN, newUser));

    }

    @Test
    void createNull() {
        assertThrows(IllegalArgumentException.class, ()-> service.create(null));

    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User("Duplicate", "alla@yandex.ru", "newPass", true, Collections.singleton(Role.ROLE_USER))));
    }

    @Test
    void delete() {
        service.delete(USER_1.getId());
        assertMatch(service.getAll(), List.of(USER_2, USER_3, ADMIN));

    }
    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, ()-> service.delete(1));

    }

    @Test
    void get() {
        User user = service.get(USER_1.getId());
        assertMatch(user, USER_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, ()-> service.get(1));
    }

    @Test
    void getByEmail() {
        User user = service.getByEmail("alla@yandex.ru");
        assertMatch(user, USER_1);

    }

    @Test
    void getByEmailNotFound() {
        assertThrows(NotFoundException.class, ()-> service.getByEmail("allaNotExist@yandex.ru"));

    }
    @Test
    void getByEmailNull() {
        assertThrows(IllegalArgumentException.class, ()-> service.getByEmail(null));

    }

    @Test
    void update() {
        User updated = new User(USER_1);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singleton(Role.ROLE_ADMIN));
        service.update(new User(updated));
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(), List.of(USER_1, USER_2, USER_3, ADMIN));
    }

    @Test
    void enable() {

        service.enable(USER_ID, false);
        assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        assertTrue(service.get(USER_ID).isEnabled());
    }
}