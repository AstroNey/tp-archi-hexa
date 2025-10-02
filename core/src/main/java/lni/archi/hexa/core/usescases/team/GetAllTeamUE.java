package lni.archi.hexa.core.usescases.team;

import lni.archi.hexa.core.domain.TeamDN;
import lni.archi.hexa.core.enums.exception.jobException.TeamErrorMessage;
import lni.archi.hexa.core.exceptions.ExceptionTools;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.exceptions.tech.TechException;
import lni.archi.hexa.core.ports.data.repositories.ITeamRepoPT;

import java.util.List;

public class GetAllTeamUE {

    private final ITeamRepoPT teamRepo;

    public GetAllTeamUE(ITeamRepoPT teamRepo) {
        this.teamRepo = teamRepo;
    }

    public List<TeamDN> execute() {
        try {
            return this.teamRepo.getAllTeam();
        } catch (TechException e) {
            throw ExceptionTools.ProcessTechException(e);
        } catch (JobException e) {
            throw e;
        } catch (Exception e) {
            throw new JobException(e.getMessage(), TeamErrorMessage.UNKNOWN_ERROR);
        }
    }
}
