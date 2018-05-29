package ru.neustupov.restvoting.web.menu;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.restvoting.TestUtil;
import ru.neustupov.restvoting.model.Menu;
import ru.neustupov.restvoting.service.MenuService;
import ru.neustupov.restvoting.web.AbstractControllerTest;
import ru.neustupov.restvoting.web.json.JsonUtil;

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.restvoting.MenuTestData.*;
import static ru.neustupov.restvoting.RestaurantTestData.RUSSIA_ID;
import static ru.neustupov.restvoting.TestUtil.userHttpBasic;
import static ru.neustupov.restvoting.UserTestData.ADMIN;
import static ru.neustupov.restvoting.UserTestData.USER;

public class AdminMenuRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = AdminMenuRestController.REST_URL + '/';

    @Autowired
    private MenuService menuService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RUSSIA_MENU_ID1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RUSSIA_MENU1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RUSSIA_MENU_ID1)
                .with(userHttpBasic(ADMIN))
                .param("restId", "100002"))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(menuService.getAll(RUSSIA_ID), RUSSIA_MENU2, MENU_TODAYS_WITH_MEALS);
    }

    @Test
    public void testCreate() throws Exception {
        Menu expected = new Menu(getCreated());
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .param("restId", "100002")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Menu returned = TestUtil.readFromJson(action, Menu.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(menuService.getAll(RUSSIA_ID), RUSSIA_MENU1, RUSSIA_MENU2, MENU_TODAYS_WITH_MEALS, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN))
                .param("restId", "100002"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RUSSIA_MENU1, RUSSIA_MENU2, MENU_TODAYS_WITH_MEALS)));
    }

    @Test
    public void testUpdate() throws Exception {
        Menu updated = new Menu(getCreated());
        updated.setId(100007);
        updated.setAddDate(Date.valueOf("2017-06-01"));
        mockMvc.perform(put(REST_URL + RUSSIA_MENU_ID1)
                .with(userHttpBasic(ADMIN))
                .param("restId", "100002")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(menuService.get(100007), updated);
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
                .param("restId", "100002")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .param("restId", "100002")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}
