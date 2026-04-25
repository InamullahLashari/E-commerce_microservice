package com.example.authservice.exception;

public class ProductNotFoundException extends  RuntimeException{

    public ProductNotFoundException(String message)
    {
        super(message);
    }


}
