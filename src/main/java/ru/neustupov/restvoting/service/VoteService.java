package ru.neustupov.restvoting.service;

import ru.neustupov.restvoting.model.Vote;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {

    Vote create(Vote vote, int userId, int restId);

    void delete(int id, int userId) throws NotFoundException;

    Vote get(int id) throws NotFoundException;

    void update(Vote vote, int userId, int restId);

    List<Vote> getAll();

    List<Vote> getAllByRest(int restId);

    Vote getByUserIdAndRestId(int userId, int restId);

    Vote getByUserIdAndDate(int userId);

    List<Vote> getTodaysVotes();

    List<Vote> getTodaysVotesOfAuthUserAndRest(int restId);

    List<Vote> getBetween(LocalDate startDate, LocalDate endDate, int restId);
}
