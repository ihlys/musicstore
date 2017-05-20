package com.ihordev.core.jpa.testing;


public class PropertyAccessException extends RuntimeException {

    public PropertyAccessException(Throwable cause) {
        super(cause);
    }

    public PropertyAccessException() {
    }

    public PropertyAccessException(String message) {
        super(message);
    }
}
