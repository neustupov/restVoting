package ru.neustupov.restvoting.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.restvoting.model.Vote;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import static ru.neustupov.restvoting.RestaurantTestData.RUSSIA_ID;
import static ru.neustupov.restvoting.UserTestData.ADMIN_ID;
import static ru.neustupov.restvoting.UserTestData.USER_ID;
import static ru.neustupov.restvoting.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    public void delete() throws Exception {
        service.delete(VOTE4_ID, ADMIN_ID);
        assertMatch(service.getAll(), VOTES);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(VOTE1_ID, 1);
    }

    @Test
    public void create() throws Exception {
        Vote created = getCreated();
        service.create(created, USER_ID, RUSSIA_ID);
        assertMatch(service.getAll(), VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6,
                VOTE_FOR_GET_BY_USER_ID_AND_DATE, created);
    }

    @Test
    public void get() throws Exception {
        Vote actual = service.get(VOTE1_ID);
        assertMatch(actual, VOTE1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(100500);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(), VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, VOTE_FOR_GET_BY_USER_ID_AND_DATE);
    }

    @Test
    public void getByUserIdAndDate() throws Exception {
        assertMatch(service.getByUserIdAndDate(ADMIN_ID), VOTE_FOR_GET_BY_USER_ID_AND_DATE);
    }

    @Test
    public void getByUserIdAndDateNotFound() throws Exception {
        assertMatch(service.getByUserIdAndDate(USER_ID), null);
    }
}
