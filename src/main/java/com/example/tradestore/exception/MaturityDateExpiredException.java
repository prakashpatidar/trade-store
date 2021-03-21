package com.example.tradestore.exception;

public class MaturityDateExpiredException extends Exception{
    public MaturityDateExpiredException(String errorMessage){
        super(errorMessage);
    }
}
