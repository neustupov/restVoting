package ru.neustupov.restvoting.repository;

import ru.neustupov.restvoting.model.Meal;

import java.util.List;

public interface MealRepository {

    Meal save(Meal meal, int menuId);

    // false if not found
    boolean delete(int id);

    // null if not found
    Meal get(int id);

    List<Meal> getAll(int menuId);
}
