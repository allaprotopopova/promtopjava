package ru.protopopova.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.protopopova.model.Menu;
import ru.protopopova.model.Restaurant;
import ru.protopopova.model.Vote;
import ru.protopopova.service.MenuService;
import ru.protopopova.service.VoteService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    static final String REST_URL = "/rest/user";

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MenuService menuService;
    private final VoteService voteService;


    @Autowired
    public UserController(MenuService menuService, VoteService voteService) {
        this.menuService = menuService;
        this.voteService = voteService;
    }

    @GetMapping("/menu/restaurant/{id}")
    public List<Menu> getMenuByRestaurant(@PathVariable("id") int id) {
        log.info("get menu for restaurant id={}", id);
        return menuService.getByRestaurant(id);
    }

    @GetMapping("/menu/date/{date}")
    public List<Menu> getMenuByDate(@PathVariable("date") LocalDate localDate) {
        log.info("get menu for date={}", localDate);
        return menuService.getByDate(localDate);
    }

    @GetMapping("/menu/by")
    public Menu getMenuByRestaurantAndDate(@RequestParam("restaurantId") int restaurantId,
                                           @RequestParam("date") LocalDate date) {
        log.info("get menu for restaurantId={} and date={}", restaurantId, date);
        return menuService.getByRestaurantAndDate(restaurantId, date);
    }

    @GetMapping("/menu")
    public List<Menu> getCurrentMenu() {
        log.info("get current menu for date {}", LocalDate.now());
        return menuService.getCurrent();
    }

    //vote
    @PostMapping("/votes")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Vote saveVote(@RequestBody Vote vote) {
        log.info("saveVote vote {}", vote);
        return voteService.save(vote);
    }

    @GetMapping("votes/history/restaurant/{id}")
    public Map<LocalDate, List<Vote>> getVotesHistoryByRestaurant(@PathVariable("id") int restaurant_id) {
        log.info("get votes history for restaurantId={}", restaurant_id);
        return voteService.getHistoryByRestaurant(restaurant_id);
    }

    @GetMapping("/votes/history/date/{date}")
    public Map<Restaurant, List<Vote>> getVotesByDate(@PathVariable("date") LocalDate localDate) {
        log.info("get votes for date {}", localDate);
        return voteService.getByDate(localDate);
    }

    @GetMapping("/votes")
    public List<Vote> getVotesByDateAndRestaurant(@RequestParam("date") LocalDate localDate,
                                                  @RequestParam("id") int restaurantId) {
        log.info("get votes for restaurantId={} and date {}", restaurantId, localDate);
        return voteService.getByDateAndRestaurant(localDate, restaurantId);
    }

    @GetMapping("/vote")
    public Vote getByUserAndDate(@RequestParam("user") int userId, @RequestParam("date") LocalDate date) {
        log.info("get vote for userId={} and date {}", userId, date);
        return voteService.getByUserAndDate(userId, date);
    }

}
