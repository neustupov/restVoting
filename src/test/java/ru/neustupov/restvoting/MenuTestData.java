package ru.neustupov.restvoting;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.neustupov.restvoting.model.Menu;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.neustupov.restvoting.model.AbstractBaseEntity.START_SEQ;
import static ru.neustupov.restvoting.web.json.JsonUtil.writeIgnoreProps;

public class MenuTestData {

    public static final int RUSSIA_MENU_ID1 = START_SEQ + 7;
    public static final int UKRAINE_MENU_ID = START_SEQ + 8;
    public static final int RUSSIA_MENU_ID2 = START_SEQ + 12;
    public static final int MENU_TODAYS_WITH_MEALS_ID = START_SEQ + 13;

    public static final Menu RUSSIA_MENU1 = new Menu(RUSSIA_MENU_ID1, LocalDate.of(2015, 5, 1));
    public static final Menu RUSSIA_MENU2 = new Menu(RUSSIA_MENU_ID2, LocalDate.of(2015, 5, 2));

    public static final Menu MENU_TODAYS_WITH_MEALS = new Menu(MENU_TODAYS_WITH_MEALS_ID, LocalDate.now());

    public static Menu getCreated() {
        return new Menu(null, LocalDate.of(2015, 6, 1));
    }

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "meals", "restaurant", "addDate");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("meals", "restaurant", "addDate").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Menu... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "addDate"));
    }

    public static ResultMatcher contentJson(Menu expected) {
        return content().json(writeIgnoreProps(expected, "addDate"));
    }
}
