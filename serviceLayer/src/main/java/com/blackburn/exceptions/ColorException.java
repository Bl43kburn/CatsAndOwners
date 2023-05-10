package com.blackburn.exceptions;

public class ColorException extends RuntimeException {

    private ColorException(String message) {
        super(message);
    }

    public static ColorException UnsupportedColorException() {
        throw new ColorException("This color is not supported in the system");
    }

    public static ColorException DuplicateColorAddition() {
        throw new ColorException("An attempt was made to add a color that is already present in the system");
    }
}
