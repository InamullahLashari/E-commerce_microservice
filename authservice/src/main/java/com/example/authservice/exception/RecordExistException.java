package com.example.authservice.exception;

public class RecordExistException extends RuntimeException {
    public RecordExistException(String message) {
        super(message);
    }
}