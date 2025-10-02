package lni.archi.hexa.core.exceptions;

import lni.archi.hexa.core.enums.exception.jobException.PersonErrorMessage;
import lni.archi.hexa.core.enums.exception.jobException.TeamErrorMessage;
import lni.archi.hexa.core.enums.exception.techException.ITechErrorMessage;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.exceptions.tech.TechException;

import static lni.archi.hexa.core.enums.exception.techException.PersonErrorMessage.*;
import static lni.archi.hexa.core.enums.exception.techException.PersonErrorMessage.CANNOT_GET_ALL_PERSON_FOR_TEAM;
import static lni.archi.hexa.core.enums.exception.techException.TeamErrorMessage.*;

public class ExceptionTools {

    public static JobException ProcessTechException(TechException techException) {
        // SWITCH with java 21
        ITechErrorMessage errorMessage = techException.getErrorMessage();
        if (errorMessage.equals(CANNOT_CREATE_PERSON)) {
            return new JobException("We can't create a person.", PersonErrorMessage.CANNOT_CREATE_PERSON);
        }
        else if (errorMessage.equals(CANNOT_GET_PERSON_BY_ID_NULL)) {
            return new JobException("The ID you have chosen was not valid.", PersonErrorMessage.INVALID_ID);
        }
        else if (errorMessage.equals(CANNOT_GET_PERSON_BY_ID_NOT_FOUND)) {
            return new JobException("The ID you have chosen not corresponding to a person.", PersonErrorMessage.INVALID_ID);
        }
        else if (errorMessage.equals(CANNOT_GET_ALL_PERSON)) {
            return new JobException("There are a problem to get all persons.", PersonErrorMessage.PERSONS_NOT_FOUND);
        }
        else if (errorMessage.equals(CANNOT_GET_ALL_PERSON_FOR_TEAM)) {
            return new JobException("There are a problem to get all persons to link them with new team.", PersonErrorMessage.PERSON_NOT_FOUND);
        }
        else if (errorMessage.equals(CANNOT_GET_ALL_PERSON_IN_TEAM)) {
            return new JobException("There are a problem to get all persons in team.", PersonErrorMessage.PERSONS_NOT_FOUND);
        }
        else if (errorMessage.equals(CANNOT_CREATE_TEAM)) {
            return new JobException("We can't create a team.", TeamErrorMessage.CANNOT_CREATE_TEAM);
        }
        else if (errorMessage.equals(CANNOT_GET_TEAM_BY_ID_NULL)) {
            return new JobException("The ID you have chosen was not valid.", TeamErrorMessage.INVALID_ID);
        }
        else if (errorMessage.equals(CANNOT_GET_TEAM_BY_ID_NOT_FOUND)) {
            return new JobException("The ID you have chosen not corresponding to a team.", TeamErrorMessage.TEAM_NOT_FOUND);
        }
        else if (errorMessage.equals(CANNOT_GET_ALL_TEAM)) {
            return new JobException("There are a problem to get all teams.", TeamErrorMessage.TEAMS_NOT_FOUND);
        }
        else if (errorMessage.equals(CANNOT_LINK_PERSON_TO_TEAM)) {
            return new JobException("There are a problem to link persons to the team.", TeamErrorMessage.LINK_ERROR);
        }

        // need unknown error as default
        return new JobException("An unknown error occurred.", PersonErrorMessage.UNKNOWN_ERROR);
    }
}
