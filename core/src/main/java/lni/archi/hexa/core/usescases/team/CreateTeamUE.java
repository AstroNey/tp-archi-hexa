package lni.archi.hexa.core.usescases.team;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.domain.TeamDN;
import lni.archi.hexa.core.enums.exception.jobException.TeamErrorMessage;
import lni.archi.hexa.core.exceptions.ExceptionTools;
import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.exceptions.job.JobListException;
import lni.archi.hexa.core.exceptions.tech.TechException;
import lni.archi.hexa.core.ports.data.repositories.ITeamRepoPT;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class CreateTeamUE {

    private final ITeamRepoPT teamRepo;
    private final List<JobException> exceptions = new ArrayList<>();

    public CreateTeamUE(ITeamRepoPT teamRepo) {
        this.teamRepo = teamRepo;
    }

    @Transactional
    public TeamDN execute(String name, List<PersonDN> members) throws JobException {
        try {
            checkParams(name, members);
            return this.teamRepo.createTeam(name, members);
        } catch (InvalidParamsExeception e) {
            this.exceptions.add(e);
        } catch (TechException e) {
            this.exceptions.add(ExceptionTools.ProcessTechException(e));
        } catch (Exception e) {
            this.exceptions.add(new JobException(e.getMessage(), TeamErrorMessage.UNKNOWN_ERROR));
        }
        throw new JobListException("Errors occurred while creating person", exceptions);
    }

    private void checkParams(String name, List<PersonDN> members) {
        checkName(name);
        checkMembers(members);
    }

    private void checkName(String name) {
        if (name == null || name.length() < 5 || name.length() > 20) {
            this.exceptions.add(new InvalidParamsExeception("Name must be between 5 and 20 characters", TeamErrorMessage.INVALID_NAME));
        }
    }

    private void checkMembers(List<PersonDN> members) {
        if (members == null || members.size() < 6 || members.size() > 15) {
            this.exceptions.add(new InvalidParamsExeception("Team must have at least 6 person and at most 15 persons", TeamErrorMessage.INVALID_MEMBERS));
        }
        else {
            for (PersonDN member : members) {
                checkMember(member);
            }
        }
    }

    private void checkMember(PersonDN member) {
        if (member.getAge() < 18) {
            this.exceptions.add(new InvalidParamsExeception("All persons in team must be at least 18 years old", TeamErrorMessage.INVALID_AGE_MEMBERS));
        }
    }
}
