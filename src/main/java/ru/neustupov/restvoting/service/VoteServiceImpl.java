package ru.neustupov.restvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.neustupov.restvoting.AuthorizedUser;
import ru.neustupov.restvoting.model.Vote;
import ru.neustupov.restvoting.repository.VoteRepository;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.neustupov.restvoting.util.ValidationUtil.checkNotFoundWithId;
import static ru.neustupov.restvoting.util.ValidationUtil.checkTimeForVote;

@Service
public class VoteServiceImpl implements VoteService{

    final LocalTime STOP_TIME = LocalTime.of(11, 0);

    private final VoteRepository repository;

    @Autowired
    public VoteServiceImpl(VoteRepository repository){
        this.repository = repository;
    }

    @Override
    public Vote create(Vote vote, int userId, int restId) {
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote, userId, restId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public Vote get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public void update(Vote vote, int userId, int restId) {
        Assert.notNull(vote, "vote must not be null");
        checkTimeForVote(STOP_TIME);
        checkNotFoundWithId(repository.save(vote, userId, restId), vote.getId());
    }

    @Override
    public List<Vote> getAll() {
        return repository.getAll();
    }

    public Vote getByUserIdAndDate(int userId) {
        Assert.notNull(userId, "userId must not be null");
        return repository.getByUserIdAndDate(userId);
    }

    public List<Vote> getTodaysVotes(){
        return repository.getTodaysVotes();
    }

    public List<Vote> getTodaysVotesOfAuthUserAndRest(int restId) {
        int userId = AuthorizedUser.get().getId();
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(restId, "restId must not be null");
        return repository.getTodaysVotesOfAuthUserAndRest(userId, restId);
    }

    public List<Vote> getBetween(LocalDate startDate, LocalDate endDate, int restId){
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate  must not be null");
        Assert.notNull(restId, "restId  must not be null");
        return repository.getBetween(startDate, endDate, restId);
    }
}
