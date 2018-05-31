package ru.neustupov.restvoting.web.menu;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.neustupov.restvoting.service.MenuService;
import ru.neustupov.restvoting.web.AbstractControllerTest;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.restvoting.TestUtil.userHttpBasic;
import static ru.neustupov.restvoting.UserTestData.USER;

public class ProfileMenuRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = ProfileMenuRestController.REST_URL + '/';

    @Test
    public void testGetTodaysMenuWithMeals() throws Exception {

        String testDate = new SimpleDateFormat("yyyy-MM-dd").format(Date.valueOf(LocalDate.now()));

        mockMvc.perform(get(REST_URL + "/todays")
                .with(userHttpBasic(USER))
                .param("restId", "100002"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.addDate", is(testDate)));
    }
}
