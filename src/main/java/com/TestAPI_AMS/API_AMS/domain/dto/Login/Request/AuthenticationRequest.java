package com.TestAPI_AMS.API_AMS.domain.dto.Login.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AuthenticationRequest {
    private String usuario;
    private String clave;
    private String empresa;
}
