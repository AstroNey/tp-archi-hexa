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
        checkName(name);
        checkMembers(members);
    }

    private void checkName(String name) {
        if (name == null || name.length() < 5 || name.length() > 20) {
            throw new InvalidParamsExeception("Name must be between 5 and 20 characters and not null");
        }
    }

    private void checkMembers(List<PersonDN> members) {
        if (members == null || members.size() < 6 || members.size() > 15) {
            throw new InvalidParamsExeception("Team must have at least 6 person and at most 15 persons");
        }
        else {
            for (PersonDN member : members) {
                checkMember(member);
            }
        }
    }

    private void checkMember(PersonDN member) {
        if (member.getAge() < 18) {
            throw new InvalidParamsExeception("All persons in team must be at least 18 years old");
        }
    }
}
