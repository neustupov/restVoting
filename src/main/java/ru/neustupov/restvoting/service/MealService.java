package ru.neustupov.restvoting.service;

import ru.neustupov.restvoting.model.Meal;
import ru.neustupov.restvoting.to.MealTo;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import java.util.List;

public interface MealService {

    Meal create(Meal meal, int menuId);

    void delete(int id, int menuId) throws NotFoundException;

    Meal get(int id, int menuId) throws NotFoundException;

    void update(Meal meal, int menuId);

    void update(MealTo mealTo, int menuId);

    List<Meal> getAll(int menuId);

    Meal getWithMenu(int id);
}
