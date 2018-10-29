package ru.neustupov.restvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.neustupov.restvoting.model.Menu;
import ru.neustupov.restvoting.repository.MenuRepository;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;
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
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
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
        return repository.findByRestaurantIdAndAddDate(restId, LocalDate.now());
    }

    @Override
    public Collection<Menu> findAllTodaysMenus() {
        return repository.findAllTodaysMenus(LocalDate.now());
    }

    @Override
    public List<Menu> getBetweenDates(LocalDate startDate, LocalDate endDate, int restId) {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate  must not be null");
        Assert.notNull(restId, "restId  must not be null");
        return repository.getBetween(startDate, endDate, restId);
    }
}
