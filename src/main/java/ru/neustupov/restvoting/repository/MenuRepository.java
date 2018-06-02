package ru.neustupov.restvoting.repository;

import ru.neustupov.restvoting.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository {

    Menu save(Menu menu, int restId);

    // false if not found
    boolean delete(int id);

    // null if not found
    Menu get(int id);

    List<Menu> getAll(int restId);

    default Menu findByRestaurantIdAndAddDate(int id, LocalDate currDate) { throw new UnsupportedOperationException();}

    List<Menu> getBetween(LocalDate startDate, LocalDate endDate, int restId);
}
