package Enums;

public enum RegistrationResult {

    SUCCESSFULLY_REGISTERED(0, "Sie haben sich erfolgreich registriert."),
    SUCCESSFULLY_CREATED_NEW_USER(1, "Das neue Benutzerkonto wurde erfolgreich angelegt."),
    SUCCESSFULLY_CHANGED_PASSWORD(3, "Sie haben ihr Passwort erfolgreich geändert.");

    private final int code;
    private final String description;

    private RegistrationResult(int code, String description) {
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
