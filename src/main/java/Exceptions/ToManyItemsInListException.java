package Exceptions;

public class ToManyItemsInListException extends Exception {
    public ToManyItemsInListException() {super("This list has to many items. Four items is max.");}
}
