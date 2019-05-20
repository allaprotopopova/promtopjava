package ru.protopopova.service;

import ru.protopopova.model.Restaurant;
import ru.protopopova.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface VoteService {

    Map<LocalDate, List<Vote>> getVotesHistoryByRestaurant(int restaurant_id);
    Map<Restaurant, List<Vote>> getVotesByDate(LocalDate localDate);
    List<Vote> getVotesByDateAndRestaurant(LocalDate localDate, int restaurantId);
    Vote getByUserAndDate(int userId, LocalDate date);


    boolean delete(int id);
    Vote save(Vote vote);





}
