package ru.neustupov.restvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.neustupov.restvoting.model.Menu;
import ru.neustupov.restvoting.model.Restaurant;
import ru.neustupov.restvoting.repository.MenuRepository;
import ru.neustupov.restvoting.repository.RestaurantRepository;
import ru.neustupov.restvoting.repository.VoteRepository;
import ru.neustupov.restvoting.to.RestaurantWithTodaysMenu;
import ru.neustupov.restvoting.to.VoteCount;
import ru.neustupov.restvoting.util.RestaurantsUtil;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static ru.neustupov.restvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final MenuRepository menuRepository;

    private final VoteRepository voteRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository, MenuRepository menuRepository,
                                 VoteRepository voteRepository) {
        this.restaurantRepository = repository;
        this.menuRepository = menuRepository;
        this.voteRepository = voteRepository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    @Cacheable("restaurants")
    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @Override
    public Restaurant getWithVotes(int id) {
        return checkNotFoundWithId(restaurantRepository.getWithVotes(id), id);
    }

    @Override
    public List<RestaurantWithTodaysMenu> getAllRestaurantsWithMealsFromTodaysMenu() {
        Collection<Restaurant> allRestaurants = restaurantRepository.getAll();

        Collection<Menu> allTodaysMenus = menuRepository.findAllTodaysMenus(LocalDate.now());

        Collection<VoteCount> voteCounts = voteRepository.getVoteCountForCurrentDate();

        return RestaurantsUtil.convertRestaurantToRestaurantTo(allRestaurants, allTodaysMenus, voteCounts);
    }
}
