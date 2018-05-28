package ru.neustupov.restvoting;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.neustupov.restvoting.model.Role;
import ru.neustupov.restvoting.model.User;
import ru.neustupov.restvoting.web.json.JsonUtil;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.neustupov.restvoting.model.AbstractBaseEntity.START_SEQ;
import static ru.neustupov.restvoting.web.json.JsonUtil.writeIgnoreProps;

public class UserTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru","password", Date.from(Instant.now()),  EnumSet.of(Role.ROLE_USER));
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@yandex.ru", "admin", Date.from(Instant.now()), EnumSet.of(Role.ROLE_ADMIN, Role.ROLE_USER));

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles", "votes", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "votes", "password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "registered", "password"));
    }

    public static ResultMatcher contentJson(User expected) {
        return content().json(writeIgnoreProps(expected, "registered", "password"));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
