package ru.neustupov.restvoting.repository;

import ru.neustupov.restvoting.model.Menu;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface MenuRepository {

    Menu save(Menu menu, int restId);

    // false if not found
    boolean delete(int id);

    // null if not found
    Menu get(int id);

    List<Menu> getAll(int restId);

    default Menu getWithRestaurant(int id) {
        throw new UnsupportedOperationException();
    }

    default Menu findByRestaurantIdAndAddDate(int id, Date currDate) { throw new UnsupportedOperationException();}

    default Menu getWithRestaurantAndMeals(int id) {
        throw new UnsupportedOperationException();
    }

    List<Menu> getBetween(LocalDate startDate, LocalDate endDate, int restId);
}
