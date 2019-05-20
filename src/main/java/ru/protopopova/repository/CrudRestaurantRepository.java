package ru.protopopova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.protopopova.model.Dish;
import ru.protopopova.model.Restaurant;
import ru.protopopova.model.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Override
    List<Restaurant> findAll();

    @Override
    Optional<Restaurant> findById(Integer integer);

    @Override
    @Transactional
    <S extends Restaurant> S save(S s);

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant d WHERE d.id=:id")
    int delete(@Param("id") int id);


}
