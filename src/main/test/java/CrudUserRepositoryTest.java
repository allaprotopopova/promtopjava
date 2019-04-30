import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.protopopova.ApplicationConfig;
import ru.protopopova.repository.CrudUserRepository;


@SpringJUnitConfig(ApplicationConfig.class)
class CrudUserRepositoryTest {

    @Autowired
    CrudUserRepository repository;

    @Test
    void findAll() {
        System.out.println(repository.findAll());
    }
}
