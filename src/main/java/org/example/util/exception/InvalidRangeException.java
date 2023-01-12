package org.example.util.exception;

public class InvalidRangeException extends RuntimeException {
    public InvalidRangeException() {
    }

    public InvalidRangeException(String message) {
        super(message);
    }
}
