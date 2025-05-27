package com.TestAPI_AMS.API_AMS.custom.exceptions;

public class CustomBadRequestException extends Exception{
    public CustomBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
