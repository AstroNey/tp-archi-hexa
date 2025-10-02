package lni.archi.hexa.core.usescases.team;

import lni.archi.hexa.core.domain.TeamDN;
import lni.archi.hexa.core.enums.exception.jobException.TeamErrorMessage;
import lni.archi.hexa.core.exceptions.ExceptionTools;
import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.exceptions.tech.TechException;
import lni.archi.hexa.core.ports.data.repositories.ITeamRepoPT;

public class GetTeamByIdUE {

    public ITeamRepoPT teamRepo;

    public GetTeamByIdUE(ITeamRepoPT teamRepo) {
        this.teamRepo = teamRepo;
    }

    public TeamDN execute(int id) {
        try {
            checkId(id);
            return this.teamRepo.getTeamById(id);
        } catch (TechException e) {
            throw ExceptionTools.ProcessTechException(e);
        } catch (JobException e) {
            throw e;
        } catch (Exception e) {
            throw new JobException(e.getMessage(), TeamErrorMessage.UNKNOWN_ERROR);
        }
    }

    private void checkId(int id) {
        if (id <= 0) {
            throw new InvalidParamsExeception("The ID must be greater than zero.", TeamErrorMessage.INVALID_ID);
        }
    }
}
