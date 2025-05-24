package org.example.tutorial11.exception;

public class LinkDoesNotExistException extends Exception{
    public LinkDoesNotExistException(String message) {
        super(message);
    }
}
