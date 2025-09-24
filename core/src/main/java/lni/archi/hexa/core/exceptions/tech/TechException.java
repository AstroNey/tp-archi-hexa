package lni.archi.hexa.core.exceptions.tech;

import lni.archi.hexa.core.enums.exception.RequestTypeE;

public class TechException extends RuntimeException {

    private RequestTypeE typeOfRequest;

    public TechException(String message) {
        super(message);
    }

    public TechException(String message, Throwable cause) {
        super(message, cause);
    }

    public TechException(String message, RequestTypeE typeOfRequest) {
        super(message);
        this.typeOfRequest = typeOfRequest;
    }

    public TechException(String message, RequestTypeE typeOfRequest, Throwable cause) {
        super(message, cause);
        this.typeOfRequest = typeOfRequest;
    }
}
