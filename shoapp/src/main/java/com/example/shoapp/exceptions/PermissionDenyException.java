package com.example.shoapp.exceptions;

public class PermissionDenyException extends Exception {
    public PermissionDenyException(String message) {
        super(message);
    }
}
