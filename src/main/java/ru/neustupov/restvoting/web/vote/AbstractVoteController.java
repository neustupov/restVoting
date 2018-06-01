package ru.neustupov.restvoting.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.restvoting.AuthorizedUser;
import ru.neustupov.restvoting.model.Vote;
import ru.neustupov.restvoting.service.VoteService;
import ru.neustupov.restvoting.util.DateTimeUtil;
import ru.neustupov.restvoting.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.neustupov.restvoting.util.ValidationUtil.checkNew;

public abstract class AbstractVoteController {

    private static final Logger log = LoggerFactory.getLogger(AbstractVoteController.class);

    static final LocalTime STOP_TIME = LocalTime.of(11, 00, 00, 00);

    @Autowired
    private VoteService service;

    public Vote get(int id) {
        int userId = AuthorizedUser.get().getId();
        log.info("get vote {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.get().getId();
        log.info("delete vote {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<Vote> getAll() {
        log.info("getAll votes");
        return service.getAll();
    }

    public List<Vote> getAllByRest(int restId) {
        log.info("getAllByRest {}", restId);
        return service.getAllByRest(restId);
    }

    public Vote create(Vote vote, int restId) {
        int userId = AuthorizedUser.get().getId();
        checkNew(vote);
        log.info("create {} for user {}", vote, userId);
        return service.create(vote, userId, restId);
    }

    public void update(int id, Vote vote, int restId) {
        int userId = AuthorizedUser.get().getId();
        vote.setId(id);
        ValidationUtil.checkTimeForVote(STOP_TIME);
        log.info("update {} for user {} and restaurant {}", vote, userId, restId);
        service.update(vote, userId, restId);
    }

    public Vote getByUserIdAndRestId(int restId) {
        int userId = AuthorizedUser.get().getId();
        log.info("getByUserIdAndRestId userId {} restId {}", restId, userId);
        return service.getByUserIdAndRestId(userId, restId);
    }

    public Vote getByUserIdAndDate() {
        int userId = AuthorizedUser.get().getId();
        return service.getByUserIdAndDate(userId);
    }

    public List<Vote> getTodaysVotes() {
        return service.getTodaysVotes();
    }

    public List<Vote> getTodaysVotesOfAuthUserAndRest(int restId){
        return service.getTodaysVotesOfAuthUserAndRest(restId);
    }

    public List<Vote> getBetween(LocalDate startDate, LocalDate endDate, int restId) {
        log.info("getBetween dates({} - {}) for restaurant {}", startDate, endDate, restId);

        List<Vote> mealsDateFiltered = service.getBetween(
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE, restId);

        return mealsDateFiltered;
    }
}
