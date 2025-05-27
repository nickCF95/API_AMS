package com.TestAPI_AMS.API_AMS.custom.exceptions;

public class CustomServerErrorException extends Exception {
    public CustomServerErrorException(String errorMessage) {
        super(errorMessage);
    }
}
