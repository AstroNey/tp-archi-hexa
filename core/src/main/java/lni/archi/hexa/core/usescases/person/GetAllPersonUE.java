package lni.archi.hexa.core.usescases.person;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.ports.data.repositories.IPersonRepoPT;

import java.util.List;

public class GetAllPersonUE {

    private IPersonRepoPT personRepo;

    public GetAllPersonUE(IPersonRepoPT personRepo) {
        this.personRepo = personRepo;
    }

    public List<PersonDN> execute() {
        try {
            return this.personRepo.getAllPerson();
        } catch (Exception e) {
            throw new JobException(e.getMessage());
        }
    }
}
