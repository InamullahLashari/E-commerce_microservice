package com.ecom.order_service.exception;

public class RecordExistException extends RuntimeException {
    public RecordExistException(String message) {
        super(message);
    }
}