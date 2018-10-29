package ru.neustupov.restvoting.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.neustupov.restvoting.model.Meal;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantWithTodaysMenu {

    private int id;
    private String name;
    private Integer numberOfVotes;
    private List<Meal> mealsFromTodaysMenu;
}
