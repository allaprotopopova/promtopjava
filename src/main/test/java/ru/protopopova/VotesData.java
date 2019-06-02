package ru.protopopova;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.protopopova.model.Dish;
import ru.protopopova.model.Menu;
import ru.protopopova.model.Vote;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.protopopova.DishesData.*;
import static ru.protopopova.RestarauntTestData.*;
import static ru.protopopova.TestUtil.readListFromJsonMvcResult;
import static ru.protopopova.UserTestData.*;

public class VotesData {

    public static final Vote VOTE_1 = new Vote(100004, LocalDate.of(2019, 04, 30), USER_1, RESTAURANT_1);
    public static final Vote VOTE_2 = new Vote(100005, LocalDate.of(2019, 04, 30), USER_2, RESTAURANT_1);
    public static final Vote VOTE_3 = new Vote(100006, LocalDate.of(2019, 04, 30), USER_3, RESTAURANT_2);
    public static final Vote VOTE_4 = new Vote(100007, LocalDate.of(2019, 05, 14), USER_1, RESTAURANT_3);
    public static final Vote VOTE_5 = new Vote(100008, LocalDate.of(2019, 05, 14), USER_1, RESTAURANT_1);
    public static final Vote VOTE_6 = new Vote(100009, LocalDate.of(2019, 05, 14), USER_2, RESTAURANT_1);
    public static final Vote VOTE_7 = new Vote(100010, LocalDate.now(), USER_1, RESTAURANT_3);
    public static final Vote VOTE_8 = new Vote(100011, LocalDate.now(), USER_2, RESTAURANT_1);
    public static final Vote VOTE_9 = new Vote(100012, LocalDate.now(), USER_3, RESTAURANT_1);

    public static List<Vote> allVotes = List.of(VOTE_1, VOTE_2, VOTE_3, VOTE_4, VOTE_5, VOTE_6, VOTE_7, VOTE_8, VOTE_9);


    public static Vote getCreated() {
        return new Vote(USER_1, RESTAURANT_1);
    }

    public static List<Vote> getVotes(Predicate<Vote> filter) {
        return allVotes.stream().filter(filter).sorted(Comparator.comparingInt(v -> v.getId())).collect(Collectors.toList());
    }

    public static <T> void assertMap(Map<T, List<Vote>> votesHistory, Map<T, List<Vote>> expectedHistory) {
        assertThat(votesHistory.keySet()).usingFieldByFieldElementComparator().isEqualTo(expectedHistory.keySet());
        votesHistory.forEach((k, v) ->
        {
            assertMatch(v, expectedHistory.get(k));
        });
    }


    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user", "restaurant").isEqualTo(expected);
    }

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user", "restaurant");
        RestarauntTestData.assertMatch(actual.getRestaurant(), expected.getRestaurant());
        UserTestData.assertMatch(actual.getUser(), expected.getUser());
    }

    public static void assertMatchWothoutVotes(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user", "restaurant");
        RestarauntTestData.assertMatchWithoutVotes(actual.getRestaurant(), expected.getRestaurant());
        UserTestData.assertMatch(actual.getUser(), expected.getUser());
    }

    public static ResultMatcher getVotesMatcher(Vote... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Vote.class), List.of(expected));
    }

    private VotesData() {
    }
}
