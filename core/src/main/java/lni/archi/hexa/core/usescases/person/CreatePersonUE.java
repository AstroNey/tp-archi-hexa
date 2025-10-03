package lni.archi.hexa.core.usescases.person;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.enums.exception.jobException.IJobErrorMessage;
import lni.archi.hexa.core.enums.exception.jobException.PersonErrorMessage;
import lni.archi.hexa.core.exceptions.ExceptionTools;
import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.exceptions.job.JobListException;
import lni.archi.hexa.core.exceptions.tech.TechException;
import lni.archi.hexa.core.ports.data.repositories.IPersonRepoPT;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class CreatePersonUE {

    private final IPersonRepoPT personRepo;

    public CreatePersonUE(IPersonRepoPT personRepo) {
        this.personRepo = personRepo;
    }

    private final List<JobException> exceptions = new ArrayList<>();

    @Transactional
    public PersonDN execute(String firstName, String name, int age) {
        try {
            this.exceptions.clear();
            checkParams(firstName, name, age);
            return this.personRepo.createPerson(firstName, name, age);
        } catch (InvalidParamsExeception e) {
            this.exceptions.add(e);
        } catch (TechException e) {
            this.exceptions.add(ExceptionTools.ProcessTechException(e));
        } catch (Exception e) {
            this.exceptions.add(new JobException(e.getMessage(), PersonErrorMessage.UNKNOWN_ERROR));
        }
        throw new JobListException("Errors occurred while creating person", exceptions);
    }

    private void checkParams(String firstName, String name, int age) {
        checkFirstName(firstName);
        checkName(name);
        checkAge(age);
    }

    private void checkFirstName(String firstName) {
        if (firstName == null || firstName.length() < 2 || firstName.length() > 30) {
            this.exceptions.add(new InvalidParamsExeception("Firstname must be between 2 and 30 characters", PersonErrorMessage.INVALID_FIRSTNAME));
        }
    }

    private void checkName(String name) {
        if (name == null || name.length() < 2 || name.length() > 40) {
            this.exceptions.add(new InvalidParamsExeception("Name must be between 1 and 40 characters", PersonErrorMessage.INVALID_NAME));
        }
    }

    private void checkAge(int age) {
        if (age <= 0) {
            this.exceptions.add(new InvalidParamsExeception("Age must be greater than 0", PersonErrorMessage.INVALID_AGE));
        }
    }
}
