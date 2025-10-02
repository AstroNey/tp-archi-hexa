package lni.archi.hexa.hexarest.controllers;

import lni.archi.hexa.core.enums.exception.jobException.IJobErrorMessage;
import lni.archi.hexa.core.enums.exception.jobException.PersonErrorMessage;
import lni.archi.hexa.core.enums.exception.jobException.TeamErrorMessage;
import lni.archi.hexa.core.exceptions.job.JobException;
import org.springframework.http.HttpStatus;

public class ControllerTools {


    public static HttpStatus FindHttpStatus(JobException e) {
        // SWITCH with java 21
        IJobErrorMessage errorMessage = e.getErrorMessage();
        if (
            errorMessage.equals(PersonErrorMessage.CANNOT_CREATE_PERSON) ||
            errorMessage.equals(TeamErrorMessage.CANNOT_CREATE_TEAM)     ||
            errorMessage.equals(PersonErrorMessage.INVALID_ID)           ||
            errorMessage.equals(PersonErrorMessage.INVALID_NAME)         ||
            errorMessage.equals(PersonErrorMessage.INVALID_FIRSTNAME)    ||
            errorMessage.equals(PersonErrorMessage.INVALID_AGE)          ||
            errorMessage.equals(TeamErrorMessage.INVALID_ID)             ||
            errorMessage.equals(TeamErrorMessage.INVALID_NAME)           ||
            errorMessage.equals(TeamErrorMessage.INVALID_AGE_MEMBERS)    ||
            errorMessage.equals(TeamErrorMessage.INVALID_MEMBERS)        ||
            errorMessage.equals(TeamErrorMessage.LINK_ERROR)
        ) {
            return HttpStatus.BAD_REQUEST;
        }
        else if (
            errorMessage.equals(PersonErrorMessage.PERSON_NOT_FOUND) ||
            errorMessage.equals(PersonErrorMessage.PERSONS_NOT_FOUND) ||
            errorMessage.equals(TeamErrorMessage.TEAM_NOT_FOUND) ||
            errorMessage.equals(TeamErrorMessage.TEAMS_NOT_FOUND)) {
            return HttpStatus.NOT_FOUND;
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
