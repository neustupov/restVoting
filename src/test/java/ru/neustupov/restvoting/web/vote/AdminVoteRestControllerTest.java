package ru.neustupov.restvoting.web.vote;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.restvoting.TestUtil;
import ru.neustupov.restvoting.model.Vote;
import ru.neustupov.restvoting.service.VoteService;
import ru.neustupov.restvoting.util.exception.ErrorType;
import ru.neustupov.restvoting.web.AbstractControllerTest;
import ru.neustupov.restvoting.web.json.JsonUtil;

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.restvoting.RestaurantTestData.RUSSIA;
import static ru.neustupov.restvoting.TestUtil.userHttpBasic;
import static ru.neustupov.restvoting.UserTestData.ADMIN;
import static ru.neustupov.restvoting.VoteTestData.*;

public class AdminVoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminVoteRestController.REST_URL + '/';

    @Autowired
    private VoteService voteService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE4_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE4));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, TODAYS_VOTE)));
    }

    @Test
    public void testGetTodaysVotes() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "/todays")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)));
    }

    @Test
    public void testGetTodaysVotesOfAuthUserAndRest() throws Exception{
        TestUtil.print(mockMvc.perform(get(REST_URL + "/todaysByRest")
                .param("restId", "100002")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)));
    }

    @Test
    public void testCreate() throws Exception {
        Vote expected = new Vote(ADMIN, Date.valueOf("2017-05-01"), RUSSIA);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .param("restId", "100002")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Vote returned = TestUtil.readFromJson(action, Vote.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(voteService.getAll(), VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, VOTE_FOR_GET_BY_USER_ID_AND_DATE, expected);
    }

    @Test
    public void testCreateInvalid() throws Exception {
        Vote invalid = new Vote(null, null, null, null);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }

    // it not working after STOP TIME //
    @Ignore
    @Test
    public void testUpdate() throws Exception {
        Vote updated = new Vote(VOTE1);
        updated.setDate(Date.valueOf("2017-06-01"));
        mockMvc.perform(put(REST_URL + VOTE1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .param("restId", "100002"))
                .andExpect(status().isOk());

        assertMatch(voteService.get(VOTE1_ID), updated);
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Vote invalid = new Vote(null, null, null, null);
        mockMvc.perform(put(REST_URL + VOTE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + VOTE4_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(voteService.getAll(), VOTES);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}
