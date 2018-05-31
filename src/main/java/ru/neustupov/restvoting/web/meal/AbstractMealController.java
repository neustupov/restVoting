package ru.neustupov.restvoting.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.restvoting.model.Meal;
import ru.neustupov.restvoting.service.MealService;

import java.util.List;

import static ru.neustupov.restvoting.util.ValidationUtil.checkNew;

public class AbstractMealController {

    private static final Logger log = LoggerFactory.getLogger(AbstractMealController.class);

    @Autowired
    private MealService service;

    public Meal create(Meal meal, int menuId){
        checkNew(meal);
        log.info("create {} for menu {}", meal, menuId);
        return service.create(meal, menuId);
    }

    public void delete(int id){
        log.info("delete meal {}", id);
        service.delete(id);
    }

    public Meal get(int id) {
        log.info("get meal {}", id);
        return service.get(id);
    }

    public void update(int id, Meal meal, int menuId){
        meal.setId(id);
        log.info("update {} with id = {} for menu {}", meal, id, menuId);
        service.update(meal, menuId);
    }

    public List<Meal> getAll(int menuId){
        log.info("getAll meals for menu {}", menuId);
        return service.getAll(menuId);
    }
}
