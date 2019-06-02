package ru.protopopova;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.protopopova.model.Dish;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.protopopova.TestUtil.readListFromJsonMvcResult;


public class DishesData {

    public static final Dish DISH6 = new Dish(6, "Гренки", 10.0);
    public static final Dish DISH7 = new Dish(7, "Суп-лапша куриный", 65.0);
    public static final Dish DISH8 = new Dish(8, "Котлеты по-Киевски", 135.5);
    public static final Dish DISH9 = new Dish(9, "Рогалик", 20.0);
    public static final Dish DISH10 = new Dish(10, "Морс 1 л", 120.0);
    public static final Dish DISH11 = new Dish(11, "Рис", 50.0);
    public static final Dish DISH12 = new Dish(12, "Картофельное пюре", 65.0);
    public static final Dish DISH13 = new Dish(13, "Треска в кляре", 235.0);
    public static final Dish DISH14 = new Dish(14, "Бульон с яйцом", 70.0);
    public static final Dish DISH15 = new Dish(15, "Шашлык", 240.0);
    public static final Dish DISH16 = new Dish(16, "Пахлава", 67.0);
    public static final Dish DISH17 = new Dish(17, "Биточки куриные", 130.0);
    public static final Dish DISH18 = new Dish(18, "Салат Цезарь", 80.0);
    public static final Dish DISH19 = new Dish(19, "Чай", 15.0);
    public static final Dish DISH20 = new Dish(20, "Салат Витаминный", 55.0);

    public static final List<Dish> DISHES = List.of(DISH6, DISH7, DISH8, DISH9, DISH10, DISH11, DISH12, DISH13, DISH14, DISH15, DISH16, DISH17, DISH18, DISH19, DISH20);

    public static Dish getCreated() {
        return new Dish(null, "Новое блюдо", 45.0);
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static ResultMatcher getDishMatcher(Dish... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Dish.class), List.of(expected));
    }

    public static ResultMatcher getDishMatcher(List<Dish> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Dish.class), expected);
    }


    private DishesData() {
    }
}
