package ru.protopopova;

import org.assertj.core.api.ObjectAssert;
import ru.protopopova.model.Dish;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;


public class DishesData {

    public static final Dish DISH6 = new Dish(6, "Гренки");
    public static final Dish DISH7 = new Dish(7, "Суп-лапша куриный");
    public static final Dish DISH8 = new Dish(8, "Котлеты по-Киевски");
    public static final Dish DISH9 = new Dish(9, "Рогалик");
    public static final Dish DISH10 = new Dish(10, "Морс 1 л");
    public static final Dish DISH11 = new Dish(11, "Рис");
    public static final Dish DISH12 = new Dish(12, "Картофельное пюре");
    public static final Dish DISH13 = new Dish(13, "Треска в кляре");
    public static final Dish DISH14 = new Dish(14, "Бульон с яйцом");
    public static final Dish DISH15 = new Dish(15, "Шашлык");
    public static final Dish DISH16 = new Dish(16, "Пахлава");
    public static final Dish DISH17 = new Dish(17, "Биточки куриные");
    public static final Dish DISH18 = new Dish(18, "Салат Цезарь");
    public static final Dish DISH19 = new Dish(19, "Чай");
    public static final Dish DISH20 = new Dish(20, "Салат Витаминный");

    public static final List<Dish> DISHES = List.of(DISH6, DISH7, DISH8, DISH9, DISH10, DISH11, DISH12, DISH13, DISH14, DISH15, DISH16, DISH17, DISH18, DISH19, DISH20);

    public static Dish getCreated() {
        return new Dish(null, "Новое блюдо");
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }


    private DishesData() {
    }
}
