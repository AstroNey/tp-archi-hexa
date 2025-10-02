package lni.archi.hexa.core.usescases.person;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.enums.exception.jobException.PersonErrorMessage;
import lni.archi.hexa.core.exceptions.ExceptionTools;
import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.exceptions.tech.TechException;
import lni.archi.hexa.core.ports.data.repositories.IPersonRepoPT;

public class GetPersonByIdUE {

    private final IPersonRepoPT personRepo;

    public GetPersonByIdUE(IPersonRepoPT personRepo) {
        this.personRepo = personRepo;
    }

    public PersonDN execute(int id) {
        try {
            checkId(id);
            return this.personRepo.getPersonById(id);
        } catch (TechException e) {
            throw ExceptionTools.ProcessTechException(e);
        } catch (JobException e) {
            throw e;
        } catch (Exception e) {
            throw new JobException(e.getMessage(), PersonErrorMessage.UNKNOWN_ERROR);
        }
    }

    private void checkId(int id) {
        if (id <= 0) {
            throw new InvalidParamsExeception("The ID must be greater than zero.", PersonErrorMessage.INVALID_ID);
        }
    }
}
