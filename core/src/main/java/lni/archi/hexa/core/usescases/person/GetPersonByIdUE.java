package lni.archi.hexa.core.usescases.person;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.ports.data.repositories.IPersonRepoPT;

public class GetPersonByIdUE {

    private final IPersonRepoPT personRepo;

    public GetPersonByIdUE(IPersonRepoPT personRepo) {
        this.personRepo = personRepo;
    }

    public PersonDN execute(int id) {
        try {
            if (id <= 0) {
                throw new InvalidParamsExeception("The ID must be greater than zero.");
            }
            return this.personRepo.getPersonById(id);
        } catch (InvalidParamsExeception e) {
            throw e;
        } catch (Exception e) {
            throw new JobException(e.getMessage());
        }
    }
}
