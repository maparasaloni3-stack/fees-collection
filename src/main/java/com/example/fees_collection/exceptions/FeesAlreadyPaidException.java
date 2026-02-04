package com.example.fees_collection.exceptions;

public class FeesAlreadyPaidException extends RuntimeException {

    public FeesAlreadyPaidException(String message) {
        super(message);
    }
}
