package com.blackburn.exceptions;

public class SearchException extends RuntimeException {
    private SearchException(String message){
        super(message);
    }

    public static SearchException CatOwnerNotFound(){
        return new SearchException("Cat owner not found");
    }

    public static SearchException CatNotFound(){
        return new SearchException("Cat not found");
    }

    public static SearchException ColorNotFound(){
        return new SearchException("Color not found");
    }

    public static SearchException RequestNotFound(){
        return new SearchException("Request not found");
    }
}
