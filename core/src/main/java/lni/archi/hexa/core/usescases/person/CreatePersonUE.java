package lni.archi.hexa.core.usescases.person;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.ports.data.repositories.IPersonRepoPT;

import javax.transaction.Transactional;

public class CreatePersonUE {

    private IPersonRepoPT personRepo;

    public CreatePersonUE(IPersonRepoPT personRepo) {
        this.personRepo = personRepo;
    }

    @Transactional
    public PersonDN execute(String firstName, String name, int age) {
        try {
            checkParams(firstName, name, age);
            return this.personRepo.createPerson(firstName, name, age);
        } catch (Exception e) {
            throw new JobException(e.getMessage());
        }
    }

    private void checkParams(String firstName, String name, int age) {
        boolean result = true;
        String invalidParams = "";

        if (firstName == null || firstName.length() < 2 || firstName.length() > 30) {
            result = false;
            invalidParams = "First name must be between 2 and 30 characters and not null";
        }
        if (name == null || name.length() < 2 || name.length() > 40) {
            result = false;
            invalidParams = "Name must be between 1 and 40 characters and not null";
        }
        if (age <= 0) {
            result = false;
            invalidParams = "Age must be greater than 0";
        }

        if (!result) {
            throw new InvalidParamsExeception(invalidParams);
        }
    }
}
