package ru.neustupov.restvoting.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.neustupov.restvoting.model.Restaurant;
import ru.neustupov.restvoting.repository.RestaurantRepository;

import java.util.List;

@Repository
public class DataJpaRestaurantRepositoryImpl implements RestaurantRepository{

    @Autowired
    private CrudRestaurantRepository crudRestaurantRepository;

    @Override
    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id) {
        return crudRestaurantRepository.delete(id) != 0;
    }

    @Override
    public Restaurant get(int id) {
        return crudRestaurantRepository.findById(id).orElse(null);
    }

    @Override
    public List<Restaurant> getAll() {
        return crudRestaurantRepository.findAll();
    }

    @Override
    public Restaurant getWithVotes(int id) {
        return crudRestaurantRepository.getWithVotes(id);
    }
}
