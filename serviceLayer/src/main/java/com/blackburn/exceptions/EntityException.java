package com.blackburn.exceptions;

public class EntityException extends RuntimeException {
    protected EntityException(String message){
        super(message);
    }

    public static EntityException InitializationError(){
        return new EntityException("Required fields are not provided or invalid");
    }
}
