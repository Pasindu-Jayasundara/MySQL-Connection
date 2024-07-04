package com;

public class NewException extends Exception{
    
    public NewException(String message, Exception e) {
        super(message,e);
    }

    public NewException(String message) {
        super(message);
    }
    
    
    
}
