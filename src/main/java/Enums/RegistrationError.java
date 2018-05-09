package Enums;

public enum RegistrationError {

    NO_ERROR_DETECTED(0, ""),
    ACCESS_TOKEN_IS_NOT_VALID_ERROR(1, "Der Access Token ist nicht gültig."),
    EMAIL_EXISTS_ALREADY_ERROR(2, "Für die angegebene E-mail existiert bereits ein Benutzeraccount."),
    EMAIL_NOT_VALID_ERROR(3, "Die angegebene E-mail ist nicht gültig."),
    CREATING_NEW_USER_FAILED_ERROR(4, "Das anlegen eines neuen Benutzerkontos ist aus unbekannten gründen gescheitert. Bitte wiederholen sie die Eingabe."),
    STRING_ROLE_DOES_NOT_MATCH_ROLE_ERROR(5, "Die angegebene Rolle exisitert nicht."),
    PASSWORD_DOES_NOT_MATCH_ERROR(6, "Die Passworteingabe war falsch. Bitte probieren sie es erneut!"),
    REPEAT_PASSWORD_DOES_NOT_MATCH_ERROR(7, "Die Passwörter stimmen nicht überein."),
    REPEAT_NEW_PASSWORD_DOES_NOT_MATCH_ERROR(8, "Die neuen Passwörter stimmen nicht überein."),
    CHANGING_PASSWORD_DOES_NOT_WORK(9, "Es ist ein fehler aufgetreten!");

    private final int code;
    private final String description;

    private RegistrationError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}
