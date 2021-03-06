package ru.neustupov.restvoting.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.neustupov.restvoting.model.Meal;
import ru.neustupov.restvoting.model.Menu;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import static ru.neustupov.restvoting.MealTestData.MEAL_IN_MENU;
import static ru.neustupov.restvoting.MenuTestData.*;
import static ru.neustupov.restvoting.RestaurantTestData.NULL_ID;
import static ru.neustupov.restvoting.RestaurantTestData.RUSSIA_ID;

public class MenuServiceTest extends AbstractServiceTest {
    @Autowired
    private MenuService service;

    @Test
    public void delete() throws Exception {
        service.delete(RUSSIA_MENU_ID1);
        assertMatch(service.getAll(RUSSIA_ID), RUSSIA_MENU2, MENU_TODAYS_WITH_MEALS);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(100);
    }

    @Test
    public void create() throws Exception {
        Menu created = getCreated();
        service.create(created, RUSSIA_ID);
        assertMatch(service.getAll(RUSSIA_ID), RUSSIA_MENU1, RUSSIA_MENU2, MENU_TODAYS_WITH_MEALS, created);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createWithNullRestaurant() {
        Menu created = getCreated();
        service.create(created, NULL_ID);
    }

    @Test
    public void get() throws Exception {
        Menu actual = service.get(RUSSIA_MENU_ID1);
        assertMatch(actual, RUSSIA_MENU1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(100);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(RUSSIA_ID), RUSSIA_MENU1, RUSSIA_MENU2, MENU_TODAYS_WITH_MEALS);
    }

    @Test
    public void getTodaysMenuWithMeals() throws Exception {
        Menu menu = service.getTodaysMenuWithMeals(100002);
        assertMatch(MENU_TODAYS_WITH_MEALS, menu);
        ru.neustupov.restvoting.MealTestData.assertMatch(MEAL_IN_MENU, (Meal) menu.getMeals().toArray()[0]);
    }
}
