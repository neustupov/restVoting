package ru.neustupov.restvoting.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.restvoting.model.Menu;
import ru.neustupov.restvoting.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaMenuRepositoryImpl implements MenuRepository {

    @Autowired
    private CrudMenuRepository crudMenuRepository;

    @Autowired
    private CrudRestaurantRepository crudRestaurantRepository;

    @Transactional
    @Override
    public Menu save(Menu menu, int restId) {
        if (!menu.isNew() && get(menu.getId()) == null) {
            return null;
        }
        menu.setRestaurant(crudRestaurantRepository.getOne(restId));
        return crudMenuRepository.save(menu);
    }

    @Override
    public boolean delete(int id) {
        return crudMenuRepository.delete(id) != 0;
    }

    @Override
    public Menu get(int id) {
        return crudMenuRepository.findById(id).orElse(null);
    }

    @Override
    public List<Menu> getAll(int restId) {
        return crudMenuRepository.getAll(restId);
    }

    @Override
    public Menu getWithRestaurant(int id) {
        return crudMenuRepository.getWithRestaurant(id);
    }

    @Override
    public Menu findByRestaurantIdAndAddDate(int restId, LocalDate currDate) {
        return crudMenuRepository.findByRestaurantIdAndAddDate(restId, currDate);
    }

    @Override
    public Menu getWithRestaurantAndMeals(int id) {
        return crudMenuRepository.getWithRestaurantAndMeals(id);
    }

    @Override
    public List<Menu> getBetween(LocalDate startDate, LocalDate endDate, int userId) {
        return crudMenuRepository.getBetween(startDate, endDate, userId);
    }
}
