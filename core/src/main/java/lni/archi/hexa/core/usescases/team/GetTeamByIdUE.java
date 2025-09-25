package lni.archi.hexa.core.usescases.team;

import lni.archi.hexa.core.domain.TeamDN;
import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.ports.data.repositories.ITeamRepoPT;

public class GetTeamByIdUE {

    public ITeamRepoPT teamRepo;

    public GetTeamByIdUE(ITeamRepoPT teamRepo) {
        this.teamRepo = teamRepo;
    }

    public TeamDN execute(int id) {
        try {
            if (id <= 0) {
                throw new InvalidParamsExeception("Id must be greater than zero.");
            }
            return this.teamRepo.getTeamById(id);
        } catch (InvalidParamsExeception e) {
            throw e;
        } catch (Exception e) {
            throw new JobException(e.getMessage());
        }
    }
}
