package com.blackburn.exceptions;

public class UserException extends RuntimeException {
    private UserException(String message) {
        super(message);
    }

    public static UserException NotFound() {
        return new UserException("User not found");
    }

    public static UserException AlreadyExists() {
        return new UserException("The user already exists in the system");
    }

    public static UserException EmptyCredentials() {
        return new UserException("Credentials are empty");
    }
}
