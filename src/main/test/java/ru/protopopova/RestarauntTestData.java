package ru.protopopova;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.protopopova.model.Restaurant;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.protopopova.TestUtil.readListFromJsonMvcResult;


public class RestarauntTestData {

    public static final Restaurant RESTAURANT_1 = new Restaurant(1, "Прага", "улица Арбат", 2);
    public static final Restaurant RESTAURANT_2 = new Restaurant(2, "Корчма", "Рязанский проспект", 0);
    public static final Restaurant RESTAURANT_3 = new Restaurant(3, "Пушкин", "Пушкинская площадь", 1);


    public static Restaurant getCreated() {
        return new Restaurant(null, "Новый", "Новая улица", 0);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    static void assertMatchWithoutVotes(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "votes");
    }

    public static ResultMatcher getRestaurantMatcher(Restaurant... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Restaurant.class), List.of(expected));
    }


    private RestarauntTestData() {
    }
}
