package ru.protopopova;

import ru.protopopova.model.Restaurant;
import ru.protopopova.model.Role;
import ru.protopopova.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.protopopova.UserTestData.ADMIN_ID;
import static ru.protopopova.UserTestData.USER_ID;
import static ru.protopopova.model.AbstractEntity.START_SEQ;




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

    private RestarauntTestData() {
    }
}
