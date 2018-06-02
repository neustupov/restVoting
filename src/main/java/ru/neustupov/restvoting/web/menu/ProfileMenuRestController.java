package ru.neustupov.restvoting.web.menu;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.neustupov.restvoting.model.Menu;

@RestController
@RequestMapping(ProfileMenuRestController.REST_URL)
public class ProfileMenuRestController extends AbstractMenuController{

    static final String REST_URL = "/rest/profile/menus";

    @Override
    @GetMapping(value = "/todays", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu getTodaysMenuWithMeals(@RequestParam("restId") int restId) {
        return super.getTodaysMenuWithMeals(restId);
    }
}
