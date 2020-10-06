package learn.foraging.domain;

import learn.foraging.data.DataException;
import learn.foraging.data.ForagerRepository;
import learn.foraging.models.Forage;
import learn.foraging.models.Forager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.stream.Collectors;

@Service
public class ForagerService {

    private final ForagerRepository repository;

    public ForagerService(ForagerRepository repository) {
        this.repository = repository;
    }

    public List<Forager> findByState(String stateAbbr) {
        return repository.findByState(stateAbbr);
    }

    public List<Forager> findAllForagers() {
        Result<Forager> result = new Result<>();
       List<Forager> foragers = repository.findAll();
       if(foragers.isEmpty() || foragers.size() == 0) {
            result.addErrorMessage("There are no foragers");
            return null;
       }
       return foragers;
    }

    public List<Forager> findByLastName(String prefix) {
        return repository.findAll().stream()
                .filter(i -> i.getLastName().startsWith(prefix))
                .collect(Collectors.toList());
    }

    public Result<Forager> add(Forager forager) throws DataException {
        Result<Forager> result = validateNulls(forager);
        if(!result.isSuccess()) {
            return result;
        }

        validate(forager, result);
        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(repository.add(forager));
        return result;
    }



    private Result<Forager> validate(Forager forager, Result<Forager> result) {

        if(forager.getFirstName().isBlank()) {
            result.addErrorMessage("Forager needs a first name");
        }

        if(forager.getLastName().isBlank()) {
            result.addErrorMessage("Forager needs a last name");
        }

        if(forager.getState().isBlank()) {
            result.addErrorMessage("Forager requires a state");
        }

        if(checkForDuplicate(forager) == true) {
            result.addErrorMessage("This forager already exists");
        }

        return result;
    }

    private Result<Forager> validateNulls(Forager forager) {
        Result<Forager> result = new Result<>();

        if (forager == null) {
            result.addErrorMessage("Nothing to save.");
            return result;
        }

        if(forager.getFirstName() == null) {
            result.addErrorMessage("First name is required");
        }

        if(forager.getLastName() == null) {
            result.addErrorMessage("Last name is required");
        }

        if(forager.getState() == null) {
            result.addErrorMessage("State is required");
        }

        return result;
    }


    private boolean checkForDuplicate(Forager forager) {
        List<Forager> all = repository.findAll();

        boolean isDuplicate = all.stream().anyMatch(a -> a.getFirstName().equalsIgnoreCase(forager.getFirstName())) && all.stream().anyMatch(b -> b.getLastName().equalsIgnoreCase(forager.getLastName()))
        && all.stream().anyMatch(c -> c.getState().equalsIgnoreCase(forager.getState()));

        return isDuplicate;
    }

}
