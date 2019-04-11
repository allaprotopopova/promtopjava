package ru.protopopova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.protopopova.model.User;

import java.util.List;
public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    List<User> findAll();
}
