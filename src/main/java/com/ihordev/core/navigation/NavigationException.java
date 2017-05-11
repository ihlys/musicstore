package com.ihordev.core.navigation;


public class NavigationException extends RuntimeException {

    public NavigationException() {
    }

    public NavigationException(String message) {
        super(message);
    }

    public NavigationException(Throwable cause) {
        super(cause);
    }

    public NavigationException(String message, Throwable cause) {
        super(message, cause);
    }
}
