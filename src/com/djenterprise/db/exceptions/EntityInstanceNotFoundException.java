package com.djenterprise.db.exceptions;

public class EntityInstanceNotFoundException extends RuntimeException {

    public EntityInstanceNotFoundException( String message ) {
        super(message);
    }

    public EntityInstanceNotFoundException( String message, Throwable cause ) {
        super(message, cause);
    }
}