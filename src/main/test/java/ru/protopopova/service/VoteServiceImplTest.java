package ru.protopopova.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import ru.protopopova.model.Restaurant;
import ru.protopopova.model.Vote;
import ru.protopopova.util.IllegalVoteChangingException;
import ru.protopopova.util.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.protopopova.RestarauntTestData.RESTAURANT_1;
import static ru.protopopova.UserTestData.ADMIN;
import static ru.protopopova.UserTestData.USER_1;
import static ru.protopopova.VotesData.*;

class VoteServiceImplTest extends AbstractServiceTest {

    @Autowired
    VoteService service;

    @Test
    void getVotesHistory() {
        Map<LocalDate, List<Vote>> votesHistory = service.getVotesHistoryByRestaurant(1);
        Map<LocalDate, List<Vote>> expectedHistory = getVotes(v->v.getRestaurant().getId()==1).stream().collect(Collectors.groupingBy(Vote::getRegistered));
        assertMap(votesHistory, expectedHistory);
    }


    @Test
    void getVotesByDate() {
        Map<Restaurant, List<Vote>> votesHistory = service.getVotesByDate(LocalDate.of(2019, 04, 30));
        Map<Restaurant, List<Vote>> expectedHistory = getVotes(v->v.getRegistered().isEqual(LocalDate.of(2019, 04,30))).stream().collect(Collectors.groupingBy(Vote::getRestaurant));
        assertMap(votesHistory, expectedHistory);

    }

    @Test
    void getVotesByDateAndRestaurant() {
        List<Vote> votes = service.getVotesByDateAndRestaurant(LocalDate.of(2019, 04,30), 1);
        List<Vote> expectedVotes = getVotes(v-> v.getRegistered().isEqual(LocalDate.of(2019,04,30))&& v.getRestaurant().getId()==1);
        assertMatch(votes, expectedVotes);
    }

    @Test
    void getVotesByDateAndRestaurantNotFound() {
        List<Vote> votes = service.getVotesByDateAndRestaurant(LocalDate.of(2019, 03,30), 1);
        List<Vote> expectedVotes = getVotes(v-> v.getRegistered().isEqual(LocalDate.of(2019,03,30))&& v.getRestaurant().getId()==1);
        assertMatch(votes, expectedVotes);
    }

    @Test
    void getByUserAndDate() {
        Vote vote = service.getByUserAndDate(USER_1.getId(), LocalDate.of(2019, 04,30));
        assertMatch(vote, VOTE_1);
    }
    @Test
    void getByUserAndDateNotExist() {
        assertThrows(NotFoundException.class, ()->service.getByUserAndDate(USER_1.getId(), LocalDate.of(2019, 03,30)));
    }

    @Test
    void save() {
        Vote newVote = new Vote(ADMIN, RESTAURANT_1);
        Vote saved = service.save(newVote);
        newVote.setId(saved.getId());
        newVote.setRegistered(saved.getRegistered());
        assertMatch(saved, newVote);

    }
    @Test()
    void saveDouble() {
        if(LocalTime.now().isAfter(LocalTime.of(11,00))) {
            assertThrows(IllegalVoteChangingException.class, ()-> service.save(VOTE_7));
        } else {
            assertMatch(service.save(VOTE_7), VOTE_7);
        }
    }

    @Test
    void delete() {
        assertThrows(IllegalVoteChangingException.class, ()->service.delete(1));
    }
}
