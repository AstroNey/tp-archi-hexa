package lni.archi.hexa.core.exceptions.job;

import lni.archi.hexa.core.enums.exception.jobException.IJobErrorMessage;

public class InvalidParamsExeception extends JobException {

    public InvalidParamsExeception(String message, IJobErrorMessage jobErrorMessage) {
        super(message, jobErrorMessage);
    }
}
