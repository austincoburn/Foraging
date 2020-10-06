package learn.foraging.data;

import learn.foraging.models.Forager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForagerFileRepositoryTest {

    @Test
    void shouldFindAll() {
        ForagerFileRepository repo = new ForagerFileRepository("./data/foragers.csv");
        List<Forager> all = repo.findAll();
        assertEquals(1000, all.size());
    }

    @Test
    void shouldAdd() throws DataException {
        ForagerFileRepository repo = new ForagerFileRepository("./data/foragers.csv");
        Forager forager = new Forager();
        forager.setId(java.util.UUID.randomUUID().toString());
        forager.setFirstName("Austin");
        forager.setLastName("Coburn");
        forager.setState("WA");
        repo.add(forager);

        List<Forager> all = repo.findAll();
        assertEquals(1001, all.size());
    }


}