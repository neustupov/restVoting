package ru.neustupov.restvoting.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.restvoting.model.Menu;
import ru.neustupov.restvoting.service.MenuService;
import ru.neustupov.restvoting.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.neustupov.restvoting.util.ValidationUtil.checkNew;

public abstract class AbstractMenuController {

    private static final Logger log = LoggerFactory.getLogger(AbstractMenuController.class);

    @Autowired
    private MenuService service;

    public Menu get(int id) {
        log.info("get menu {}", id);
        return service.get(id);
    }

    public List<Menu> getAll(int restId) {
        log.info("getAll menus for restaurant {}", restId);
        return service.getAll(restId);
    }

    public Menu getTodaysMenuWithMeals(int restId){
        log.info("getTodaysMenuWithMeals for restaurant {}", restId);
        return service.getTodaysMenuWithMeals(restId);
    }

    public Menu create(Menu menu, int restId) {
        checkNew(menu);
        log.info("create {} for restaurant {}", menu, restId);
        return service.create(menu, restId);
    }

    public void update(int id, Menu menu, int restId) {
        menu.setId(id);
        log.info("update {} with id = {} for restaurant {}", menu, id, restId);
        service.update(menu, restId);
    }

    public void delete(int id) {
        log.info("delete menu {}", id);
        service.delete(id);
    }

    public List<Menu> getBetween(LocalDate startDate, LocalDate endDate, int restId) {
        log.info("getBetween dates({} - {}) for restaurant {}", startDate, endDate, restId);

        List<Menu> mealsDateFiltered = service.getBetweenDates(
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE, restId);

        return mealsDateFiltered;
    }
}
