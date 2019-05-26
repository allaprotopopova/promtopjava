package ru.protopopova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.protopopova.model.AbstractEntity;
import ru.protopopova.model.Restaurant;
import ru.protopopova.model.Vote;
import ru.protopopova.repository.CrudVoteRepository;
import ru.protopopova.util.IllegalVoteChangingException;
import ru.protopopova.util.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.protopopova.util.ValidationUtil.checkNotFound;

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
    public Map<LocalDate, List<Vote>> getHistoryByRestaurant(int restaurant_id) {
        List<Vote> allVotes = repository.findByRestaurantId(restaurant_id).orElse(new ArrayList<>());
        return allVotes.stream().sorted(Comparator.comparingInt(Vote::getId)).collect(Collectors.groupingBy(Vote::getRegistered));
    }

    @Override
    public Map<Restaurant, List<Vote>> getByDate(LocalDate localDate) {
        Assert.notNull(localDate, "date must not be null");
        List<Vote> allVotes = repository.findByRegistered(localDate).orElse(new ArrayList<>());
        return allVotes.stream().sorted(Comparator.comparingInt(Vote::getId)).collect(Collectors.groupingBy(Vote::getRestaurant));
    }

    @Override
    public List<Vote> getByDateAndRestaurant(LocalDate localDate, int restaurantId) {
        Assert.notNull(localDate, "date must not be null");
        return repository.findByRegisteredAndRestaurantId(localDate, restaurantId).orElse(new ArrayList<>()).stream().sorted(Comparator.comparingInt(Vote::getId)).collect(Collectors.toList());
    }

    @Override
    public Vote getByUserAndDate(int userId, LocalDate date) throws NotFoundException {
        Assert.notNull(date, "date must not be null");
        return checkNotFound(getVoteByUserAndDate(userId, date), "vote for userId="+userId+" and date="+date.toString()+" is not found");
    }

    private Vote getVoteByUserAndDate(int userId, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return repository.findByUserIdAndRegistered(userId, date).orElse(null);
    }


    @Override
    public boolean delete(int id) {
        throw new IllegalVoteChangingException("Vote deleting is forbidden!");
    }

    @Override
    @Transactional
    public Vote save(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        Vote dbVote = getVoteByUserAndDate(vote.getUser().getId(), LocalDate.now());
        if (dbVote!=null) {
            if (LocalTime.now().isAfter(DEADLINE_TIME)) {
                throw new IllegalVoteChangingException();
            };
        }
        return repository.save(vote);
    }
}
