package com.blackburn.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class OwnershipException extends RuntimeException {
    private OwnershipException(String message) {
        super(message);
    }

    public static OwnershipException NotOwnedEntity() {
        return new OwnershipException("User has no authority over that object");
    }
}
