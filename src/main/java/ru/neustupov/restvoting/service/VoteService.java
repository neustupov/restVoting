package ru.neustupov.restvoting.service;

import ru.neustupov.restvoting.model.Vote;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import java.util.List;

public interface VoteService {

    Vote create(Vote vote, int userId, int restId);

    void delete(int id, int userId) throws NotFoundException;

    Vote get(int id, int userId) throws NotFoundException;

    void update(Vote vote, int userId, int restId);

    List<Vote> getAll();

    List<Vote> getAllByUser(int userId);

    List<Vote> getAllByRest(int restId);

    Vote getWithUser(int id, int userId);

    Vote getWithRestaurant(int id, int restId);

    Vote getWithRestaurantAndUser(int id, int restId, int userId);

    Vote getByUserIdAndRestId(int userId, int restId);

    Vote getByUserIdAndDate(int userId);

    List<Vote> getAllForCurrentDate();
}
