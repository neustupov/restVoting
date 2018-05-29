package ru.neustupov.restvoting.service;

import ru.neustupov.restvoting.model.Menu;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import java.util.List;

public interface MenuService {

    Menu create(Menu menu, int restId);

    void delete(int id, int restId) throws NotFoundException;

    Menu get(int id) throws NotFoundException;

    void update(Menu menu, int restId);

    List<Menu> getAll(int restId);

    Menu getTodaysMenuWithMeals(int id);

    Menu getWithRestaurant(int id);

    Menu getWitMealsAndRestaurant(int id);
}
