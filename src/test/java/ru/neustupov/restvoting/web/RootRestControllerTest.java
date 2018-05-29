package ru.neustupov.restvoting.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.restvoting.TestUtil;
import ru.neustupov.restvoting.UserTestData;
import ru.neustupov.restvoting.model.Role;
import ru.neustupov.restvoting.model.User;

import java.time.Instant;
import java.util.Date;
import java.util.EnumSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.restvoting.UserTestData.ADMIN;
import static ru.neustupov.restvoting.UserTestData.USER;
import static ru.neustupov.restvoting.UserTestData.assertMatch;

public class RootRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RootRestController.REST_URL + '/';

    @Test
    public void testRegister() throws Exception {
        User expected = new User(null, "NewRegisterUser", "newregisteruser@yandex.ru",
                "newPass", Date.from(Instant.now()), EnumSet.of(Role.ROLE_USER, Role.ROLE_ADMIN));
        ResultActions action = mockMvc.perform(post(REST_URL + "register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(expected, "newPass")))
                .andExpect(status().isCreated());

        User returned = TestUtil.readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(), ADMIN, expected, USER);
    }
}
