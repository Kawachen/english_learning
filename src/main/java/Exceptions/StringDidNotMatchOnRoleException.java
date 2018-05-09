package Exceptions;

public class StringDidNotMatchOnRoleException extends Exception {

    public StringDidNotMatchOnRoleException() {
        super("The given string did not match to a role!");
    }
}
