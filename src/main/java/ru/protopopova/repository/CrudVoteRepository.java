package ru.protopopova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.protopopova.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Override
    List<Vote> findAll();

    Optional<List<Vote>> findByRestaurantId(Integer restaurantId);
    Optional<List<Vote>> findByRegisteredAndRestaurantId(LocalDate registered, Integer restaurantId);
    Optional<List<Vote>> findByRegistered(LocalDate registered);
    Optional<Vote> findByUserIdAndRegistered(int userId, LocalDate registered);

    @Override
    @Transactional
    <S extends Vote> S save(S s);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote d WHERE d.id=:id")
    int delete(@Param("id") int id);


}
