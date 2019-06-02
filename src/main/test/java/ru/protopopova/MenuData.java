package ru.protopopova;

import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.Assert;
import ru.protopopova.model.Dish;
import ru.protopopova.model.Menu;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.protopopova.DishesData.*;
import static ru.protopopova.RestarauntTestData.*;
import static ru.protopopova.TestUtil.readListFromJsonMvcResult;


public class MenuData {

    public static final Menu MENU_1 = new Menu(21, RESTAURANT_1, LocalDate.of(2019, 4, 30), List.of(DISH7, DISH8, DISH10, DISH12, DISH18));
    public static final Menu MENU_2 = new Menu(22, RESTAURANT_1, LocalDate.of(2019, 5, 1), List.of(DISH14, DISH15, DISH16, DISH18, DISH19));
    public static final Menu MENU_3 = new Menu(23, RESTAURANT_2, LocalDate.of(2019, 4, 30), List.of(DISH14, DISH15, DISH16, DISH18, DISH19));
    public static final Menu MENU_4 = new Menu(25, RESTAURANT_3, LocalDate.now(), List.of(DISH7, DISH8, DISH10, DISH12, DISH13, DISH17));
    public static final Menu MENU_5 = new Menu(26, RESTAURANT_1, LocalDate.now(), List.of(DISH10, DISH12, DISH18));

    public static final List<Dish> DISHES = List.of(DISH6, DISH7, DISH8, DISH9, DISH10, DISH11, DISH12, DISH13, DISH14, DISH15, DISH16, DISH17, DISH18, DISH19, DISH20);

    public static Menu getCreated() {
        return new Menu(null, RESTAURANT_3, LocalDate.now(), List.of(DISH6, DISH7, DISH8));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("id", "restaurant", "dishes").isEqualTo(expected);

    }

    public static void assertMatch(Menu actual, Menu expected) {
        Assert.isTrue(actual.getCreated().isEqual(expected.getCreated()), "");
        DishesData.assertMatch(actual.getDishes(), expected.getDishes());
    }

    public static ResultMatcher getMenuMatcher(Menu... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Menu.class), List.of(expected));
    }

    public static ResultMatcher getMenuMatcher(List<Menu> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Menu.class), expected);
    }


    private MenuData() {
    }
}
