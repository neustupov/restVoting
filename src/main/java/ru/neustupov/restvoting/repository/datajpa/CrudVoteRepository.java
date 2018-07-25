package ru.neustupov.restvoting.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.restvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    Vote save(Vote vote);

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v")
    List<Vote> getAll();

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restId")
    List<Vote> getAllByRestaurantId(@Param("restId") int restId);

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.id=?1")
    Vote getWithRestaurantAndUser(int id);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.restaurant.id=:restId")
    Vote getByUserIdAndRestId(@Param("userId") int userId, @Param("restId") int restId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.date=CURRENT_DATE")
    Vote getByUserIdAndDate(@Param("userId") int userId);

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.date=CURRENT_DATE")
    List<Vote> getTodaysVotes();

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.restaurant.id=:restId AND v.date=CURRENT_DATE")
    List<Vote> getTodaysVotesOfAuthUserAndRest(@Param("userId") int userId,
                                               @Param("restId") int restId);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT DISTINCT v from Vote v WHERE v.restaurant.id=:restId AND v.date BETWEEN :startDate AND :endDate ORDER BY v.date DESC")
    List<Vote> getBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("restId") int restId);
}
