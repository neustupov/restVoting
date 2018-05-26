package ru.neustupov.restvoting.web.restaurant;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.restvoting.TestUtil;
import ru.neustupov.restvoting.model.Restaurant;
import ru.neustupov.restvoting.service.RestaurantService;
import ru.neustupov.restvoting.web.AbstractControllerTest;
import ru.neustupov.restvoting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.restvoting.RestaurantTestData.*;
import static ru.neustupov.restvoting.TestUtil.userHttpBasic;
import static ru.neustupov.restvoting.UserTestData.ADMIN;
import static ru.neustupov.restvoting.UserTestData.USER;

public class AdminRestaurantRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = AdminRestaurantRestController.REST_URL + '/';

    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RUSSIA_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RUSSIA));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RUSSIA_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.getAll(), UKRAINE, U_KOLYANA, ALMAZ, FART);
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(RUSSIA);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + RUSSIA_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(restaurantService.get(RUSSIA_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Restaurant expected = new Restaurant(null, "Rostov");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Restaurant returned = TestUtil.readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(restaurantService.getAll(), RUSSIA, UKRAINE, U_KOLYANA, ALMAZ, FART, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RUSSIA, UKRAINE, U_KOLYANA, ALMAZ, FART)));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}
