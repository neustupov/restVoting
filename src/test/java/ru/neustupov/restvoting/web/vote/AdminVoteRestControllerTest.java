package ru.neustupov.restvoting.web.vote;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.restvoting.TestUtil;
import ru.neustupov.restvoting.model.Vote;
import ru.neustupov.restvoting.service.VoteService;
import ru.neustupov.restvoting.web.AbstractControllerTest;
import ru.neustupov.restvoting.web.json.JsonUtil;

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.restvoting.RestaurantTestData.RUSSIA;
import static ru.neustupov.restvoting.UserTestData.USER;
import static ru.neustupov.restvoting.UserTestData.USER_ID;
import static ru.neustupov.restvoting.VoteTestData.*;

public class AdminVoteRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = AdminVoteRestController.REST_URL + '/';

    @Autowired
    private VoteService voteService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + VOTE1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(voteService.getAll(), VOTES);
    }

    @Test
    public void testUpdate() throws Exception {
        Vote updated = new Vote(VOTE1);
        updated.setDate(Date.valueOf("2017-06-01"));
        mockMvc.perform(put(REST_URL + VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .param("restId", "100002"))
                .andExpect(status().isOk());

        assertMatch(voteService.get(VOTE1_ID, USER_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Vote expected = new Vote(USER, Date.valueOf("2017-05-01"), RUSSIA);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .param("restId", "100002")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Vote returned = TestUtil.readFromJson(action, Vote.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(voteService.getAll(), VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, expected);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6)));
    }
}
