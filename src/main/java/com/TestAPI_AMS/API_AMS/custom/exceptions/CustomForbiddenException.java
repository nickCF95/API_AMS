package com.TestAPI_AMS.API_AMS.custom.exceptions;

public class CustomForbiddenException extends Exception {
    public CustomForbiddenException(String errorMessage) {
        super(errorMessage);
    }
}
