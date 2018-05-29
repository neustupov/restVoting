package ru.neustupov.restvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.neustupov.restvoting.model.Menu;
import ru.neustupov.restvoting.repository.MenuRepository;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static ru.neustupov.restvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository repository;

    @Autowired
    public MenuServiceImpl(MenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public Menu create(Menu menu, int restId) {
        Assert.notNull(menu, "menu must not be null");
        return repository.save(menu, restId);
    }

    @Override
    public void delete(int id, int restId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, restId), id);
    }

    @Override
    public Menu get(int id) throws NotFoundException {
        Assert.notNull(id, "id must not be null");
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public void update(Menu menu, int restId) {
        Assert.notNull(menu, "menu must not be null");
        checkNotFoundWithId(repository.save(menu, restId), menu.getId());
    }

    @Override
    public List<Menu> getAll(int restId) {
        Assert.notNull(restId, "idRest must not be null");
        return repository.getAll(restId);
    }

    @Override
    public Menu getTodaysMenuWithMeals(int restId) {
        return repository.getTodaysMenuWithMeals(restId, Date.valueOf(LocalDate.now()));
    }

    @Override
    public Menu getWithRestaurant(int id) {
        return checkNotFoundWithId(repository.getWithRestaurant(id), id);
    }

    @Override
    public Menu getWitMealsAndRestaurant(int id) {
        return checkNotFoundWithId(repository.getWithRestaurantAndMeals(id), id);
    }
}
