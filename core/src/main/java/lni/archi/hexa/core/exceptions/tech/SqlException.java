package lni.archi.hexa.core.exceptions.tech;

import lni.archi.hexa.core.enums.exception.techException.ITechErrorMessage;

public class SqlException extends TechException {

    public SqlException(String message, ITechErrorMessage errorMessage) {
        super(message, errorMessage);
    }
}

