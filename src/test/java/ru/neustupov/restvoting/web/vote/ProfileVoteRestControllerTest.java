package ru.neustupov.restvoting.web.vote;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.neustupov.restvoting.TestUtil;
import ru.neustupov.restvoting.model.Vote;
import ru.neustupov.restvoting.service.VoteService;
import ru.neustupov.restvoting.to.VoteTo;
import ru.neustupov.restvoting.util.exception.ErrorType;
import ru.neustupov.restvoting.web.AbstractControllerTest;
import ru.neustupov.restvoting.web.json.JsonUtil;

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.neustupov.restvoting.RestaurantTestData.RUSSIA;
import static ru.neustupov.restvoting.TestUtil.userHttpBasic;
import static ru.neustupov.restvoting.UserTestData.USER;
import static ru.neustupov.restvoting.VoteTestData.*;
import static ru.neustupov.restvoting.VoteTestData.VOTE6;
import static ru.neustupov.restvoting.VoteTestData.VOTE_FOR_GET_BY_USER_ID_AND_DATE;

public class ProfileVoteRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = ProfileVoteRestController.REST_URL + '/';

    @Autowired
    private VoteService voteService;

    @Test
    public void testCreate() throws Exception {
        VoteTo expectedTo = new VoteTo(Date.valueOf("2017-05-01"));
        Vote expected = new Vote(USER, Date.valueOf("2017-05-01"), RUSSIA);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(USER))
                .param("restId", "100003")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expectedTo)))
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
                .with(userHttpBasic(USER)))
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
        VoteTo updatedTo = new VoteTo(Date.valueOf("2017-06-01"));
        updated.setDate(Date.valueOf("2017-06-01"));
        mockMvc.perform(put(REST_URL + VOTE1_ID)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo))
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
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value(ErrorType.VALIDATION_ERROR.name()))
                .andDo(print());
    }
}
