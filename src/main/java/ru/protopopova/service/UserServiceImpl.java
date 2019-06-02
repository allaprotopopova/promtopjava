package ru.protopopova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.protopopova.AuthorizedUser;
import ru.protopopova.model.User;
import ru.protopopova.repository.CrudUserRepository;
import ru.protopopova.util.NotFoundException;

import java.util.List;

import static ru.protopopova.util.ValidationUtil.checkNotFound;
import static ru.protopopova.util.ValidationUtil.checkNotFoundWithId;


@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final CrudUserRepository repository;

    @Autowired
    public UserServiceImpl(CrudUserRepository repository) {
        this.repository = repository;
    }

    @Override
    @CacheEvict("users")
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    @Override
    @CacheEvict("users")
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);

    }

    @Override
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Override
    @CacheEvict("users")
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), user.getId());

    }

    @Override
    @Cacheable("users")
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    @CacheEvict("users")
    public void enable(int id, boolean enable) {
        User user = get(id);
        user.setEnabled(enable);
        repository.save(user);  // !! need only for JDBC implementation

    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
