package com.test.helper;

public class ResouceNotFoundException extends RuntimeException{
    
    public  ResouceNotFoundException(String message)
    {
        super(message);
    }
    public ResouceNotFoundException()
    {
        super("Resource Not Found");
    }
}
