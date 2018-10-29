package ru.neustupov.restvoting.util;

import lombok.var;
import ru.neustupov.restvoting.model.Menu;
import ru.neustupov.restvoting.model.Restaurant;
import ru.neustupov.restvoting.model.Vote;
import ru.neustupov.restvoting.to.RestaurantWithTodaysMenu;
import ru.neustupov.restvoting.to.RestaurantWithVotes;
import ru.neustupov.restvoting.to.VoteCount;

import java.util.*;
import java.util.stream.Collectors;

public class RestaurantsUtil {

    private RestaurantsUtil() {
    }

    public static List<RestaurantWithVotes> getWithVotes(Collection<Restaurant> restaurants, List<Vote> allVotes) {
        return restaurants.stream()
                .map(restaurant -> createWithVotes(restaurant, allVotes))
                .collect(Collectors.toList());
    }

    private static RestaurantWithVotes createWithVotes(Restaurant restaurant, List<Vote> allVotes) {
        List<Vote> votes = allVotes.stream()
                .filter(vote -> vote.getRestaurant().getId().equals(restaurant.getId()))
                .collect(Collectors.toList());
        return new RestaurantWithVotes(restaurant.getId(), restaurant.getName(), votes.size());
    }

    public static List<RestaurantWithTodaysMenu> convertRestaurantToRestaurantTo(Collection<Restaurant> restaurants,
                                                                                 Collection<Menu> menus,
                                                                                 Collection<VoteCount> voteCounts) {

       return restaurants.stream().map(restaurant -> {

            var restaurantWithMeal = new RestaurantWithTodaysMenu();

            restaurantWithMeal.setId(restaurant.getId());
            restaurantWithMeal.setName(restaurant.getName());

            Optional<VoteCount> count = voteCounts
                    .stream()
                    .filter(voteCount -> voteCount.getRestId().equals(restaurant.getId()))
                    .findFirst();

            if (count.isPresent()) {
                restaurantWithMeal.setNumberOfVotes(count.get().getVoteCount().intValue());
            } else {
                restaurantWithMeal.setNumberOfVotes(0);
            }

            Optional<Menu> menu = menus
                    .stream()
                    .filter(menuOne -> menuOne.getRestaurant().getId().equals(restaurant.getId()))
                    .findFirst();

            if (menu.isPresent()) {
                restaurantWithMeal.setMealsFromTodaysMenu(new ArrayList<>(menu.get().getMeals()));
            } else {
                restaurantWithMeal.setMealsFromTodaysMenu(Collections.emptyList());
            }

            return restaurantWithMeal;

        }).collect(Collectors.toList());
    }
}
