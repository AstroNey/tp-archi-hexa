export enum TeamException {
    INVALID_ID = "The ID you have chosen was not valid.",
    TEAMS_NOT_FOUND = "There are a problem to get all teams.",
    TEAM_NOT_FOUND = "The ID you have chosen not corresponding to a team.",
    UNKNOWN_ERROR = "",
    INVALID_NAME = "Name must be between 5 and 20 characters.",
    INVALID_MEMBERS = "Team must have at least 6 person and at most 15 persons.",
    INVALID_AGE_MEMBERS = "All persons in team must be at least 18 years old.",
    LINK_ERROR = "There are a problem to link persons to the team.",
    CANNOT_CREATE_TEAM = "We can't create a team."
}
