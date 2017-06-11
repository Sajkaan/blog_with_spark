package com.teamtreehouse.blog.exceptions;

public class DaoException extends Exception {

    public final Exception originalException;

    public DaoException(Exception originalException, String message) {
        super(message);
        this.originalException = originalException;
    }

}
