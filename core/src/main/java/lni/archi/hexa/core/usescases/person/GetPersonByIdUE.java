package lni.archi.hexa.core.usescases.person;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.enums.exception.jobException.PersonErrorMessage;
import lni.archi.hexa.core.exceptions.ExceptionTools;
import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.exceptions.job.JobListException;
import lni.archi.hexa.core.exceptions.tech.TechException;
import lni.archi.hexa.core.ports.data.repositories.IPersonRepoPT;

import java.util.ArrayList;
import java.util.List;

public class GetPersonByIdUE {

    private final IPersonRepoPT personRepo;

    private final List<JobException> exceptions = new ArrayList<>();

    public GetPersonByIdUE(IPersonRepoPT personRepo) {
        this.personRepo = personRepo;
    }

    public PersonDN execute(int id) {
        try {
            this.exceptions.clear();
            checkId(id);
            return this.personRepo.getPersonById(id);
        } catch (InvalidParamsExeception e) {
            this.exceptions.add(e);
        } catch (TechException e) {
            this.exceptions.add(ExceptionTools.ProcessTechException(e));
        } catch (Exception e) {
            this.exceptions.add(new JobException(e.getMessage(), PersonErrorMessage.UNKNOWN_ERROR));
        }
        throw new JobListException("Errors occurred while creating person", exceptions);
    }

    private void checkId(int id) {
        if (id <= 0) {
            this.exceptions.add(new InvalidParamsExeception("The ID must be greater than zero.", PersonErrorMessage.INVALID_ID));
        }
    }
}
