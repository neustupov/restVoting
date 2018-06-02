package ru.neustupov.restvoting.service;

import ru.neustupov.restvoting.model.Menu;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {

    Menu create(Menu menu, int restId);

    void delete(int id) throws NotFoundException;

    Menu get(int id) throws NotFoundException;

    void update(Menu menu, int restId);

    List<Menu> getAll(int restId);

    Menu getTodaysMenuWithMeals(int id);

    List<Menu> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId);
}
