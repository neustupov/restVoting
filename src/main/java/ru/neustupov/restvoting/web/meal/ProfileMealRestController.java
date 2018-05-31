package ru.neustupov.restvoting.web.meal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.neustupov.restvoting.model.Meal;

import java.util.List;

@RestController
@RequestMapping(ProfileMealRestController.REST_URL)
public class ProfileMealRestController extends AbstractMealController{

    static final String REST_URL = "/rest/profile/meals";

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Meal> getAll(@RequestParam("menuId") int menuId) {
        return super.getAll(menuId);
    }
}
