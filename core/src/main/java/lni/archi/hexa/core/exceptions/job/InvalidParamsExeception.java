package lni.archi.hexa.core.exceptions.job;

public class InvalidParamsExeception extends JobException {

    String invalidParams;

    public InvalidParamsExeception(String message) {
        super(message);
    }

    public InvalidParamsExeception(String message, String invalidParams) {
        super(message);
        this.invalidParams = invalidParams;
    }
}
