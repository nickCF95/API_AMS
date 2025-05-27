package com.TestAPI_AMS.API_AMS.domain.dto.Login.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AuthenticationResponse {
    private String token;
    private Date iniDate;
    private Date expDate;

    public void setToken(String token) {
        if(token.contains("Bearer")){
            this.token = token.split(" ")[1];
        } else {
            this.token = token;
        }
    }

}
