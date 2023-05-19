package ru.kubsu.lab.stand.exception;

public class UserDaoException extends Exception {

    public UserDaoException(String message) {
        super(message);
    }


    public UserDaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
