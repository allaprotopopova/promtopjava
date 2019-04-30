package ru.protopopova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.protopopova.model.Dish;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {
    @Override
    List<Dish> findAll();

    @Override
    Optional<Dish> findById(Integer integer);

    @Override
    @Transactional
    <S extends Dish> S save(S s);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(@Param("id") int id);
}
