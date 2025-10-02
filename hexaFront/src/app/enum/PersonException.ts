export enum PersonException {
    INVALID_ID = "The ID you have chosen was not valid.",
    PERSONS_NOT_FOUND = "There are a problem to get all persons.",
    PERSON_NOT_FOUND = "The ID you have chosen not corresponding to a person.",
    UNKNOWN_ERROR = "",
    INVALID_FIRSTNAME = "Firstname must be between 2 and 30 characters",
    INVALID_NAME = "Name must be between 1 and 40 characters",
    INVALID_AGE = "Age must be greater than 0",
    CANNOT_CREATE_PERSON = "We can't create a person."
}
