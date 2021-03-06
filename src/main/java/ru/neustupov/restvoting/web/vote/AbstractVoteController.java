package ru.neustupov.restvoting.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.restvoting.AuthorizedUser;
import ru.neustupov.restvoting.model.Vote;
import ru.neustupov.restvoting.service.VoteService;
import ru.neustupov.restvoting.to.VoteTo;
import ru.neustupov.restvoting.util.DateTimeUtil;
import ru.neustupov.restvoting.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.neustupov.restvoting.util.ValidationUtil.checkNew;
import static ru.neustupov.restvoting.util.ValidationUtil.checkNewTo;

public abstract class AbstractVoteController {

    private static final Logger log = LoggerFactory.getLogger(AbstractVoteController.class);

    static final LocalTime STOP_TIME = LocalTime.of(11, 00, 00, 00);

    @Autowired
    private VoteService service;

    public Vote get(int id) {
        log.info("get vote {}", id);
        return service.get(id);
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

    public Vote create(Vote vote, int restId) {
        int userId = AuthorizedUser.get().getId();
        checkNew(vote);
        log.info("create {} for user {}", vote, userId);
        return service.create(vote, userId, restId);
    }

    public Vote createFromTo(VoteTo voteTo, int restId) {
        int userId = AuthorizedUser.get().getId();
        checkNewTo(voteTo);
        log.info("create {} for user {} and restaurant {}", voteTo, userId, restId);
        return service.create(new Vote(voteTo.getDate()), userId, restId);
    }

    public void update(int id, Vote vote, int restId) {
        int userId = AuthorizedUser.get().getId();
        vote.setId(id);
        ValidationUtil.checkTimeForVote(STOP_TIME);
        log.info("update {} for user {} and restaurant {}", vote, userId, restId);
        service.update(vote, userId, restId);
    }

    public void updateFromTo(int id, VoteTo voteTo, int restId){
        int userId = AuthorizedUser.get().getId();
        voteTo.setId(id);
        ValidationUtil.checkTimeForVote(STOP_TIME);
        log.info("update {} for user {} and restaurant {}", voteTo, userId, restId);
        service.update(new Vote(voteTo.getDate()), userId, restId);
    }

    public List<Vote> getTodaysVotes() {
        return service.getTodaysVotes();
    }

    public List<Vote> getTodaysVotesOfAuthUserAndRest(int restId) {
        return service.getTodaysVotesOfAuthUserAndRest(restId);
    }

    public List<Vote> getBetween(LocalDate startDate, LocalDate endDate, int restId) {
        log.info("getBetween dates({} - {}) for restaurant {}", startDate, endDate, restId);

        return service.getBetween(
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE, restId);
    }
}
