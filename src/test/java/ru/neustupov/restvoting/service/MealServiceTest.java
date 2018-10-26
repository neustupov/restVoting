package ru.neustupov.restvoting.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.neustupov.restvoting.model.Meal;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;

import static ru.neustupov.restvoting.MealTestData.*;
import static ru.neustupov.restvoting.MenuTestData.NULL_MENU;
import static ru.neustupov.restvoting.MenuTestData.RUSSIA_MENU_ID1;
import static ru.neustupov.restvoting.MenuTestData.UKRAINE_MENU_ID;

public class MealServiceTest extends AbstractServiceTest{

    @Autowired
    private MealService service;

    @Test
    public void delete() throws Exception {
        service.delete(APPLE_ID);
        assertMatch(service.getAll(RUSSIA_MENU_ID1), BOTTLE_OF_WATER);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(100500);
    }

    @Test
    public void create() throws Exception {
        Meal created = getCreated();
        service.create(created, RUSSIA_MENU_ID1);
        assertMatch(service.getAll(RUSSIA_MENU_ID1), APPLE, BOTTLE_OF_WATER, created);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createWithNullMenu(){
        Meal created = getCreated();
        service.create(created, NULL_MENU);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(APPLE_ID);
        assertMatch(actual, APPLE);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(100500);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(RUSSIA_MENU_ID1), APPLE, BOTTLE_OF_WATER);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, RUSSIA_MENU_ID1);
        assertMatch(service.get(APPLE_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        service.update(NOT_FOUND_MEAL, RUSSIA_MENU_ID1);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Meal("   ", 10), UKRAINE_MENU_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Meal("Bananas", null), UKRAINE_MENU_ID), ConstraintViolationException.class);
    }
}
