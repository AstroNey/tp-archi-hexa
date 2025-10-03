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

public class GetAllPersonUE {

    private final IPersonRepoPT personRepo;

    public GetAllPersonUE(IPersonRepoPT personRepo) {
        this.personRepo = personRepo;
    }

    private final List<JobException> exceptions = new ArrayList<>();

    public List<PersonDN> execute() {
        try {
            this.exceptions.clear();
            return this.personRepo.getAllPerson();
        } catch (InvalidParamsExeception e) {
            this.exceptions.add(e);
        } catch (TechException e) {
            this.exceptions.add(ExceptionTools.ProcessTechException(e));
        } catch (Exception e) {
            this.exceptions.add(new JobException(e.getMessage(), PersonErrorMessage.UNKNOWN_ERROR));
        }
        throw new JobListException("Errors occurred while creating person", exceptions);
    }

    public List<PersonDN> execute(Integer pageLimit, Integer page) {
        try {
            return this.personRepo.getAllPerson(pageLimit, page);
        }  catch (InvalidParamsExeception e) {
            this.exceptions.add(e);
        } catch (TechException e) {
            this.exceptions.add(ExceptionTools.ProcessTechException(e));
        } catch (Exception e) {
            this.exceptions.add(new JobException(e.getMessage(), PersonErrorMessage.UNKNOWN_ERROR));
        }
        throw new JobListException("Errors occurred while creating person", exceptions);
    }

}
