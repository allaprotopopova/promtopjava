package ru.protopopova.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.protopopova.*;
import ru.protopopova.model.Dish;
import ru.protopopova.model.Menu;
import ru.protopopova.model.Restaurant;
import ru.protopopova.model.User;
import ru.protopopova.util.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.protopopova.DishesData.DISH8;
import static ru.protopopova.DishesData.getDishMatcher;
import static ru.protopopova.MenuData.MENU_1;
import static ru.protopopova.RestarauntTestData.*;
import static ru.protopopova.TestUtil.userHttpBasic;
import static ru.protopopova.UserTestData.assertMatch;
import static ru.protopopova.UserTestData.*;
import static ru.protopopova.web.controller.AdminController.REST_URL;

class AdminControllerTest extends AbstractControllerTest {

    @Test
    void getUnauthorized() throws Exception {
        mockMvc.perform(get(REST_URL + "/menu/restaurant/1"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void getUserAuthorization() throws Exception {
        mockMvc.perform(get(REST_URL + "/menu/restaurant/1")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isForbidden());

    }

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(get(REST_URL + "/users")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getUserMatcher(USER_1, USER_2, USER_3, ADMIN));
    }

    @Test
    void getUser() throws Exception {

        mockMvc.perform(get(REST_URL + "/users/" + USER_1.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getUserMatcher(USER_1));
    }

    @Test
    void createUser() throws Exception {
        User created = UserTestData.getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL + "/users")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

        User returned = TestUtil.readFromJsonResultActions(action, User.class);
        created.setId(returned.getId());
        assertMatch(returned, created);

    }

    @Test
    void updateUser() throws Exception {
        User updated = new User(USER_1);
        updated.setEmail("new@email.com");
        mockMvc.perform(put(REST_URL + "/users/" + USER_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete(REST_URL + "/users/" + USER_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
    }

    @Test
    void getUserByMail() throws Exception {
        mockMvc.perform(get(REST_URL + "/users/by?email=" + USER_1.getEmail())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getUserMatcher(USER_1));

    }

    @Test
    void enableUser() throws Exception {
        mockMvc.perform(post(REST_URL + "/users/" + USER_1.getId() + "?enabled=false")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

    }

    @Test
    void getRestaurants() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurants")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getRestaurantMatcher(RESTAURANT_1, RESTAURANT_2, RESTAURANT_3));

    }

    @Test
    void getRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + "/restaurants/" + RESTAURANT_1.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getRestaurantMatcher(RESTAURANT_1));

    }

    @Test
    void deleteRestaurant() throws Exception {
        mockMvc.perform(delete(REST_URL + "/restaurants/" + RESTAURANT_1.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
    }

    @Test
    void createRestaurant() throws Exception {
        Restaurant created = RestarauntTestData.getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL + "/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

        Restaurant returned = TestUtil.readFromJsonResultActions(action, Restaurant.class);
        created.setId(returned.getId());
        RestarauntTestData.assertMatch(returned, created);


    }

    @Test
    void updateRestaurant() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("newName");
        mockMvc.perform(put(REST_URL + "/restaurants/" + RESTAURANT_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

    }

    @Test
    void deleteMenu() throws Exception {
        mockMvc.perform(delete(REST_URL + "/menu/" + MENU_1.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

    }

    @Test
    void saveMenu() throws Exception {
        Menu created = MenuData.getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL + "/menu?restaurantId=" + RESTAURANT_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isCreated());

        Menu returned = TestUtil.readFromJsonResultActions(action, Menu.class);
        created.setId(returned.getId());
        MenuData.assertMatch(returned, created);

    }

    @Test
    void getDishes() throws Exception {
        mockMvc.perform(get(REST_URL + "/dishes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDishMatcher(DishesData.DISHES));

    }

    @Test
    void getDishById() throws Exception {
        mockMvc.perform(get(REST_URL + "/dishes/" + DISH8.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDishMatcher(DISH8));

    }

    @Test
    void deleteDish() throws Exception {
        mockMvc.perform(delete(REST_URL + "/dishes/" + DISH8.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

    }

    @Test
    void saveDish() throws Exception {
        Dish created = DishesData.getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish returned = TestUtil.readFromJsonResultActions(action, Dish.class);
        created.setId(returned.getId());
        DishesData.assertMatch(returned, created);

    }
}