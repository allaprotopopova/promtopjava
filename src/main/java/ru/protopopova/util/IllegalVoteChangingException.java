package ru.protopopova.util;

public class IllegalVoteChangingException extends RuntimeException {


    public IllegalVoteChangingException() {
        super("Changing vote is forbidden");
    }

    public IllegalVoteChangingException(String message) {
        super(message);
    }
}
