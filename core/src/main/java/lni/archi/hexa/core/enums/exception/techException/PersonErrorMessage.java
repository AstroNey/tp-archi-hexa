package lni.archi.hexa.core.enums.exception.techException;

public enum PersonErrorMessage implements ITechErrorMessage {
    CANNOT_CREATE_PERSON,
    CANNOT_GET_PERSON_BY_ID_NULL,
    CANNOT_GET_PERSON_BY_ID_NOT_FOUND,
    CANNOT_GET_ALL_PERSON,
    NOT_MANAGED_EXCEPTION, CANNOT_GET_ALL_PERSON_FOR_TEAM
}
