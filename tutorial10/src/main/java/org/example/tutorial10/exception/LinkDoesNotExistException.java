package org.example.tutorial10.exception;

public class LinkDoesNotExistException extends Exception{
    public LinkDoesNotExistException(String message) {
        super(message);
    }
}
