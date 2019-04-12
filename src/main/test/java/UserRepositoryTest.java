import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.protopopova.config.ApplicationConfig;
import ru.protopopova.repository.UserRepository;


@SpringJUnitConfig(ApplicationConfig.class)
class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Test
    void findAll() {
        System.out.println(repository.findAll());
    }
}
