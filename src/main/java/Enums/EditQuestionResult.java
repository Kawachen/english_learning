package Enums;

public enum EditQuestionResult {

    SUCCESSFULLY_ADDED_NEW_QUESTION(0, "Frage wurde erfolgreich hinzugefügt."),
    SUCCESSFULLY_EDIT_QUESTION(1, "Die Frage wurde erfolgreich bearbeitet.");

    private final int code;
    private final String description;

    private EditQuestionResult(int code, String description) {
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
