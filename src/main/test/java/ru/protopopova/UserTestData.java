package ru.protopopova;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.protopopova.model.Role;
import ru.protopopova.model.User;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.protopopova.model.AbstractEntity.START_SEQ;


public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 3;

    public static final User USER_1 = new User(USER_ID, "Alla", "alla@yandex.ru", "password", true, Collections.singleton(Role.ROLE_USER));
    public static final User USER_2 = new User(USER_ID+1, "Artemis", "artemis@yandex.ru", "password", true, Collections.singleton(Role.ROLE_USER));
    public static final User USER_3 = new User(USER_ID+2, "Ksandr", "ksandr@yandex.ru", "password", true, Collections.singleton(Role.ROLE_USER));
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", true, Set.of(Role.ROLE_USER, Role.ROLE_ADMIN));

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "password").isEqualTo(expected);
    }

    public static User getCreated() {
        return new User("New", "new@gmail.com", "newPass", true, Collections.singleton(Role.ROLE_USER));

    }




}
