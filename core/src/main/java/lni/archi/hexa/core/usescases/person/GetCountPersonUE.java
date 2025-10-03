package lni.archi.hexa.core.usescases.person;

import lni.archi.hexa.core.enums.exception.jobException.PersonErrorMessage;
import lni.archi.hexa.core.exceptions.ExceptionTools;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.exceptions.tech.TechException;
import lni.archi.hexa.core.ports.data.repositories.IPersonRepoPT;

public class GetCountPersonUE {

    private final IPersonRepoPT personRepo;

    public GetCountPersonUE(IPersonRepoPT personRepo) {
        this.personRepo = personRepo;
    }

    public int execute() {
        try {
            return this.personRepo.getCountPersonUE();
        } catch (TechException e) {
            throw ExceptionTools.ProcessTechException(e);
        } catch (Exception e) {
            throw new JobException(e.getMessage(), PersonErrorMessage.UNKNOWN_ERROR);
        }
    }
}
