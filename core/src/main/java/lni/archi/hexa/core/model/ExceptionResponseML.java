package lni.archi.hexa.core.model;

import lni.archi.hexa.core.enums.exception.jobException.IJobErrorMessage;

public record ExceptionResponseML(IJobErrorMessage jobErrorMessage, String details) {
}
