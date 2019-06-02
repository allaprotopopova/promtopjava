package ru.protopopova.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.Assert;
import ru.protopopova.VotesData;
import ru.protopopova.model.Restaurant;
import ru.protopopova.model.Vote;
import ru.protopopova.service.RestaurantService;
import ru.protopopova.service.VoteService;
import ru.protopopova.util.json.JsonUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.protopopova.MenuData.*;
import static ru.protopopova.RestarauntTestData.RESTAURANT_1;
import static ru.protopopova.TestUtil.readFromJsonResultActions;
import static ru.protopopova.TestUtil.userHttpBasic;
import static ru.protopopova.UserTestData.ADMIN;
import static ru.protopopova.UserTestData.USER_1;
import static ru.protopopova.VotesData.*;
import static ru.protopopova.web.controller.UserController.REST_URL;

class UserControllerTest extends AbstractControllerTest {

    @Autowired
    VoteService voteService;
    @Autowired
    RestaurantService restaurantService;

    @Test
    void getUnAuthorized() throws Exception {
        mockMvc.perform(get(REST_URL + "/menu/restaurant/1"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void getMenuByRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + "/menu/restaurant/1")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getMenuMatcher(MENU_1, MENU_2, MENU_4));

    }

    @Test
    void getMenuByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "/menu/date/2019-04-30")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getMenuMatcher(MENU_1, MENU_3));

    }

    @Test
    void getMenuByRestaurantAndDate() throws Exception {
        mockMvc.perform(get(REST_URL + "/menu/by?restaurantId=1&date=2019-04-30")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getMenuMatcher(MENU_1));

    }

    @Test
    void getCurrentMenu() throws Exception {
        mockMvc.perform(get(REST_URL + "/menu")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getMenuMatcher(List.of(MENU_4, MENU_5)));

    }

    @Test
    void saveVote() throws Exception {
        Vote created = new Vote(ADMIN, new Restaurant(RESTAURANT_1));
        ResultActions action = mockMvc.perform(post(REST_URL + "/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER_1))
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

        Vote returned = readFromJsonResultActions(action, Vote.class);
        created.setId(returned.getId());
        created.getRestaurant().setVotes(RESTAURANT_1.getVotes() + 1);

        VotesData.assertMatchWothoutVotes(returned, created);


    }


    @Test
    void getVotesByDateAndRestaurant() throws Exception {
        mockMvc.perform(get(REST_URL + "/votes?date=2019-04-30&id=1")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getVotesMatcher(VOTE_1, VOTE_2));
    }

    @Test
    void getByUserAndDate() throws Exception {
        mockMvc.perform(get(REST_URL + "/vote?user=" + USER_1.getId() + "&date=2019-04-30")
                .with(userHttpBasic(USER_1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getVotesMatcher(VOTE_1));

    }
}