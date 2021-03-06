package ru.neustupov.restvoting.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.restvoting.model.Menu;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    Menu save(Menu menu);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restId ORDER BY m.id")
    List<Menu> getAll(@Param("restId") int restId);

    @EntityGraph(attributePaths = {"meals"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT DISTINCT m FROM Menu m WHERE m.restaurant.id=:restId AND m.addDate=:currDate")
    Menu findByRestaurantIdAndAddDate(@Param("restId") int restId, @Param("currDate") LocalDate currDate);

    @EntityGraph(attributePaths = {"meals"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m WHERE m.addDate=:currDate")
    Collection<Menu> findAllTodaysMenus(@Param("currDate") LocalDate currDate);

    @SuppressWarnings("JpaQlInspection")
    @EntityGraph(attributePaths = {"meals"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT DISTINCT m from Menu m WHERE m.restaurant.id=:restId AND m.addDate BETWEEN :startDate AND :endDate ORDER BY m.addDate DESC")
    List<Menu> getBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("restId") int restId);
}
