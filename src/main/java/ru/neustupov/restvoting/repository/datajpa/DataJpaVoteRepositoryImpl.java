package ru.neustupov.restvoting.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.restvoting.model.Vote;
import ru.neustupov.restvoting.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaVoteRepositoryImpl implements VoteRepository {

    @Autowired
    private CrudVoteRepository crudVoteRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Autowired
    private CrudRestaurantRepository crudRestaurantRepository;

    @Transactional
    @Override
    public Vote save(Vote vote, int userId, int restId) {
        if (!vote.isNew() && get(vote.getId()) == null) {
            return null;
        }
        vote.setUser(crudUserRepository.getOne(userId));
        vote.setRestaurant(crudRestaurantRepository.getOne(restId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudVoteRepository.delete(id, userId) != 0;
    }

    @Override
    public Vote get(int id) {
        return crudVoteRepository.getWithRestaurantAndUser(id);
    }

    @Override
    public List<Vote> getAll() {
        return crudVoteRepository.getAll();
    }

    @Override
    public List<Vote> getAllByUser(int userId) {
        return crudVoteRepository.getAllByUserId(userId);
    }

    @Override
    public List<Vote> getAllByRest(int restId) {
        return crudVoteRepository.getAllByRestaurantId(restId);
    }

    @Override
    public Vote getWithRestaurant(int id, int restId) {
        return crudVoteRepository.getWithRestaurant(id, restId);
    }

    @Override
    public Vote getWithUser(int id, int userId) {
        return crudVoteRepository.getWithUser(id, userId);
    }

    @Override
    public Vote getWithRestaurantAndUser(int id) {
        return crudVoteRepository.getWithRestaurantAndUser(id);
    }

    @Override
    public Vote getByUserIdAndRestId(int userId, int restId) {
        return crudVoteRepository.getByUserIdAndRestId(userId, restId);
    }

    @Override
    public Vote getByUserIdAndDate(int userId) {
        return crudVoteRepository.getByUserIdAndDate(userId);
    }

    public List<Vote> getTodaysVotes() {
        return crudVoteRepository.getTodaysVotes();
    }

    public List<Vote> getTodaysVotesOfAuthUserAndRest(int userId, int restId) {
        return crudVoteRepository.getTodaysVotesOfAuthUserAndRest(userId, restId);
    }

    @Override
    public List<Vote> getBetween(LocalDate startDate, LocalDate endDate, int userId) {
        return crudVoteRepository.getBetween(startDate, endDate, userId);
    }
}
