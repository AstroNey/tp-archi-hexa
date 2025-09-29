package lni.archi.hexa.core.usescases.person;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.ports.data.repositories.IPersonRepoPT;

import javax.transaction.Transactional;

public class CreatePersonUE {

    private final IPersonRepoPT personRepo;

    public CreatePersonUE(IPersonRepoPT personRepo) {
        this.personRepo = personRepo;
    }

    @Transactional
    public PersonDN execute(String firstName, String name, int age) {
        try {
            checkParams(firstName, name, age);
            return this.personRepo.createPerson(firstName, name, age);
        } catch (InvalidParamsExeception e) {
            throw e;
        } catch (Exception e) {
            throw new JobException(e.getMessage());
        }
    }

    private void checkParams(String firstName, String name, int age) {
        checkFirstName(firstName);
        checkName(name);
        checkAge(age);
    }

    private void checkFirstName(String firstName) {
        if (firstName == null || firstName.length() < 2 || firstName.length() > 30) {
            throw new InvalidParamsExeception("Firstname must be between 2 and 30 characters and not null");
        }
    }

    private void checkName(String name) {
        if (name == null || name.length() < 2 || name.length() > 40) {
            throw new InvalidParamsExeception("Name must be between 1 and 40 characters and not null");
        }
    }

    private void checkAge(int age) {
        if (age <= 0) {
            throw new InvalidParamsExeception("Age must be greater than 0");
        }
    }
}
