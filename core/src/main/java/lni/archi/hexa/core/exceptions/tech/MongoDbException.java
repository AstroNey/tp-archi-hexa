package lni.archi.hexa.core.exceptions.tech;

import lni.archi.hexa.core.enums.exception.RequestTypeE;

public class MongoDbException extends TechException {

    public MongoDbException(String message) {
        super(message);
    }

    public MongoDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public MongoDbException(String message, RequestTypeE typeOfRequest) {
        super(message, typeOfRequest);
    }

    public MongoDbException(String message, RequestTypeE typeOfRequest, Throwable cause) {
        super(message, typeOfRequest, cause);
    }
}
