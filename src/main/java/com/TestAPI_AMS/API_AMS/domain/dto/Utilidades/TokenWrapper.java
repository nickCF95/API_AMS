package com.TestAPI_AMS.API_AMS.domain.dto.Utilidades;

import org.springframework.stereotype.Component;

@Component
public class TokenWrapper {
    private String token;

    public TokenWrapper(){}

    public TokenWrapper(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
