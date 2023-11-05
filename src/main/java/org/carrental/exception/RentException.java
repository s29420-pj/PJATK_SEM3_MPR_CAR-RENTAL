package org.carrental.exception;

public class RentException extends RuntimeException {

    public RentException(String message, Exception reason){
        super(message, reason);
    }
}
