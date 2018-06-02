package ru.neustupov.restvoting.repository;

import ru.neustupov.restvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote, int userId, int restId);

    // false if not found
    boolean delete(int id, int userId);

    Vote get(int id);

    List<Vote> getAll();

    List<Vote> getAllByUser(int userId);

    List<Vote> getAllByRest(int restId);

    default Vote getWithRestaurant(int id, int restId){
        throw new UnsupportedOperationException();
    }

    default Vote getWithUser(int id, int userId){
        throw new UnsupportedOperationException();
    }

    default Vote getWithRestaurantAndUser(int id){throw new UnsupportedOperationException();}

    Vote getByUserIdAndRestId(int userId, int restId);

    Vote getByUserIdAndDate(int userId);

    List<Vote> getTodaysVotes();

    List<Vote> getTodaysVotesOfAuthUserAndRest(int userId, int restId);

    List<Vote> getBetween(LocalDate startDate, LocalDate endDate, int restId);
}
