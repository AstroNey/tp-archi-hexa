package lni.archi.hexa.core.exceptions.job;

import lni.archi.hexa.core.enums.exception.jobException.IJobErrorMessage;
import lombok.Getter;

@Getter
public class JobException extends RuntimeException {
    public final IJobErrorMessage errorMessage;

    public JobException(String message, IJobErrorMessage errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }
}
