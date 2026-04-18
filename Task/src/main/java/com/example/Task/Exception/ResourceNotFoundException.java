package com.example.Task.Exception;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);//calls the super constructor / parent constructor
    }
}
