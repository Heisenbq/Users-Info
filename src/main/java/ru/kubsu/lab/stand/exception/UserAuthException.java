package ru.kubsu.lab.stand.exception;

public class UserAuthException extends Exception{

    public UserAuthException(String message) {
        super(message);
    }

    public UserAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
