package learn.foraging.domain;

import learn.foraging.data.DataException;
import learn.foraging.data.ForagerRepositoryDouble;
import learn.foraging.models.Forager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForagerServiceTest {

    ForagerService service = new ForagerService(new ForagerRepositoryDouble());

    @Test
    void shouldAdd() throws DataException {
        Forager forager = new Forager();
        forager.setId(java.util.UUID.randomUUID().toString());
        forager.setFirstName("Jon");
        forager.setLastName("Yoodles");
        forager.setState("PA");
        Result<Forager> result = service.add(forager);
        assertEquals(true, result.isSuccess());
    }

    @Test
    void shouldNotAddDuplicate() throws DataException {
        //5e315276-f43d-4ffc-bc4e-c0aa4c0796a2,Austin,Coburn,WA
        Forager forager = new Forager();
        forager.setId("0e4707f4-407e-4ec9-9665-baca0aabe88c");
        forager.setFirstName("Jilly");
        forager.setLastName("Sisse");
        forager.setState("GA");

        Result<Forager> result = service.add(forager);
        assertEquals( "[This forager already exists]" ,result.getErrorMessages().toString());
    }


    @Test
    void shouldNotAddBlankFirstName() throws DataException {
        //5e315276-f43d-4ffc-bc4e-c0aa4c0796a2,Austin,Coburn,WA
        Forager forager = new Forager();
        forager.setId("0e4707f4-407e-4ec9-9665-bac30aabe88c");
        forager.setFirstName("");
        forager.setLastName("Sisse");
        forager.setState("GA");

        Result<Forager> result = service.add(forager);
        assertEquals( "[Forager needs a first name]" ,result.getErrorMessages().toString());
    }

    @Test
    void shouldNotAddBlankLastName() throws DataException {
        //5e315276-f43d-4ffc-bc4e-c0aa4c0796a2,Austin,Coburn,WA
        Forager forager = new Forager();
        forager.setId("0e4707f4-407e-3ec9-9665-bac30aabe88c");
        forager.setFirstName("Chickens");
        forager.setLastName("");
        forager.setState("GA");

        Result<Forager> result = service.add(forager);
        assertEquals( "[Forager needs a last name]" ,result.getErrorMessages().toString());
    }

    @Test
    void shouldNotAddNull() throws DataException {

        Forager forager = new Forager();
        forager.setId("0e4707f4-4073e-3ec9-9665-bac30aabe88c");

        Result<Forager> result = service.add(forager);
        assertFalse(result.isSuccess());
    }







}