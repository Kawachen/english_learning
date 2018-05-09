package Enums;

public enum LoginError {

    NO_ERROR_DETECTED(0, ""),
    USER_DOES_NOT_EXIST_ERROR(1, "Für diese E-mail existiert kein Benutzerkonto!"),
    PASSWORD_DOES_NOT_MATCH_ERROR(2, "Die Passworteingabe war falsch. Bitte probieren sie es erneut!");

    private final int code;
    private final String description;

    private LoginError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public int getCode() {
        return this.code;
    }
}
