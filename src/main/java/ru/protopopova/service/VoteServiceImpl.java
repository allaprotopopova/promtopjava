package ru.protopopova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.protopopova.model.Restaurant;
import ru.protopopova.model.Vote;
import ru.protopopova.repository.CrudVoteRepository;
import ru.protopopova.util.IllegalVoteChangingException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteServiceImpl implements VoteService {

    private static final LocalTime DEADLINE_TIME = LocalTime.of(11, 00);
    private final CrudVoteRepository repository;

    @Autowired
    public VoteServiceImpl(CrudVoteRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional
    public Map<LocalDate, List<Vote>> getVotesHistoryByRestaurant(int restaurant_id) {
        List<Vote> allVotes = repository.findByRestaurantId(restaurant_id).orElse(new ArrayList<>());
        return allVotes.stream().sorted(Comparator.comparingInt(v-> v.getId())).collect(Collectors.groupingBy(Vote::getRegistered));
    }

    @Override
    public Map<Restaurant, List<Vote>> getVotesByDate(LocalDate localDate) {
        List<Vote> allVotes = repository.findByRegistered(localDate).orElse(new ArrayList<>());
        return allVotes.stream().sorted(Comparator.comparingInt(v-> v.getId())).collect(Collectors.groupingBy(Vote::getRestaurant));
    }

    @Override
    public List<Vote> getVotesByDateAndRestaurant(LocalDate localDate, int restaurantId) {
        return repository.findByRegisteredAndRestaurantId(localDate, restaurantId).orElse(new ArrayList<>()).stream().sorted(Comparator.comparingInt(v-> v.getId())).collect(Collectors.toList());
    }

    @Override
    public Vote getByUserAndDate(int userId, LocalDate date) {
        return repository.findByUserIdAndRegistered(userId, date).orElse(null);
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    @Transactional
    public Vote save(Vote vote) {
        Vote dbVote = getByUserAndDate(vote.getUser().getId(), LocalDate.now());
        if (dbVote!=null) {
            if (LocalTime.now().isAfter(DEADLINE_TIME)) {
                throw new IllegalVoteChangingException();
            };
        }
        return repository.save(vote);
    }
}
