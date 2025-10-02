package lni.archi.hexa.core.exceptions.tech;

import lni.archi.hexa.core.enums.exception.techException.ITechErrorMessage;

public class MongoDbException extends TechException {

    public MongoDbException(String message, ITechErrorMessage errorMessage) {
        super(message, errorMessage);
    }
}
