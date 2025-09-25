package lni.archi.hexa.core.usescases.team;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.domain.TeamDN;
import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.ports.data.repositories.ITeamRepoPT;

import javax.transaction.Transactional;
import java.util.List;

public class CreateTeamUE {

    private final ITeamRepoPT teamRepo;

    public CreateTeamUE(ITeamRepoPT teamRepo) {
        this.teamRepo = teamRepo;
    }

    @Transactional
    public TeamDN execute(String name, List<PersonDN> members) {
        try {
            checkParams(name, members);
            return this.teamRepo.createTeam(name, members);
        } catch (InvalidParamsExeception e) {
            throw e;
        } catch (Exception e) {
            throw new JobException(e.getMessage());
        }
    }

    private void checkParams(String name, List<PersonDN> members) {
        boolean result = true;
        String invalidParams = "";

        if (name == null || name.length() < 5 || name.length() > 20) {
            result = false;
            invalidParams = "Name must be between 5 and 20 characters and not null";
        }
        if (members == null || members.size() < 6 || members.size() > 11) {
            result = false;
            invalidParams = "Team must have at least 6 person";
        }
        else {
            boolean isValid = true;
            for (int i = 0; isValid && i < members.size(); i++) {
                if (members.get(i).getAge() < 18) {
                    isValid = false;
                    result = false;
                    invalidParams = "All persons in team must be at least 18 years old";
                }
            }
        }

        if (!result) {
            throw new InvalidParamsExeception(invalidParams);
        }
    }
}
