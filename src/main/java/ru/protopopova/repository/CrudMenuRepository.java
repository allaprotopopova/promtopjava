package ru.protopopova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.protopopova.model.Menu;
import ru.protopopova.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {
    @Override
    List<Menu> findAll();

    Optional<List<Menu>> findByRestaurantId(Integer restaurantId);
    Optional<Menu> findByCreatedAndRestaurantId(LocalDate created, Integer restaurantId);
    Optional<List<Menu>> findByCreated(LocalDate created);

    @Override
    @Transactional
    <S extends Menu> S save(S s);

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu d WHERE d.id=:id")
    int delete(@Param("id") int id);

}
