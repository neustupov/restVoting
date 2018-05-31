package ru.neustupov.restvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.neustupov.restvoting.model.Meal;
import ru.neustupov.restvoting.repository.MealRepository;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import java.util.List;

import static ru.neustupov.restvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService{

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository){
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int menuId) {
        Assert.notNull(meal, "meal must not be null");
        return repository.save(meal, menuId);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Meal get(int id) throws NotFoundException {
        Assert.notNull(id, "id must not be null");
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public void update(Meal meal, int menuId) {
        Assert.notNull(meal, "meal must not be null");
        Assert.notNull(menuId, "menuId must not be null");
        checkNotFoundWithId(repository.save(meal, menuId), meal.getId());
    }

    @Override
    public List<Meal> getAll(int menuId) {
        Assert.notNull(menuId, "menuId must not be null");
        return repository.getAll(menuId);
    }

    @Override
    public Meal getWithMenu(int id) {
        return checkNotFoundWithId(repository.getWithMenu(id), id);
    }
}
