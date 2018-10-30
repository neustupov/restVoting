package ru.neustupov.restvoting.repository;

import ru.neustupov.restvoting.model.Vote;
import ru.neustupov.restvoting.to.VoteCount;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote, int userId, int restId);

    // false if not found
    boolean delete(int id, int userId);

    Vote get(int id);

    List<Vote> getAll();

    Vote getByUserIdAndDate(int userId);

    List<Vote> getTodaysVotes();

    List<Vote> getTodaysVotesOfAuthUserAndRest(int userId, int restId);

    List<VoteCount> getVoteCountForCurrentDate();

    List<Vote> getBetween(LocalDate startDate, LocalDate endDate, int restId);
}
