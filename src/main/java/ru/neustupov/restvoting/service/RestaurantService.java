package ru.neustupov.restvoting.service;

import ru.neustupov.restvoting.model.Restaurant;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import java.util.List;

public interface RestaurantService {

    Restaurant create(Restaurant restaurant);

    void delete(int id) throws NotFoundException;

    Restaurant get(int id) throws NotFoundException;

    void update(Restaurant restaurant);

    List<Restaurant> getAll();

    Restaurant getWithMenus(int id);

    Restaurant getWithVotes(int id);

    Restaurant getWithMenusAndVotes(int id);
}
