package lni.archi.hexa.core.exceptions.tech;

import lni.archi.hexa.core.enums.exception.RequestTypeE;

public class SqlException extends TechException {

    public SqlException(String message) {
        super(message);
    }

    public SqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlException(String message, RequestTypeE typeOfRequest) {
        super(message, typeOfRequest);
    }

    public SqlException(String message, RequestTypeE typeOfRequest, Throwable cause) {
        super(message, typeOfRequest, cause);
    }
}

