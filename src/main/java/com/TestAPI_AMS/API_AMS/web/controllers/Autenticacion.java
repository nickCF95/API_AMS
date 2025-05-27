package com.TestAPI_AMS.API_AMS.web.controllers;


import com.TestAPI_AMS.API_AMS.domain.dto.Login.Request.AuthenticationRequest;
import com.TestAPI_AMS.API_AMS.domain.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class Autenticacion {

    @Autowired
    private AuthenticationService authService;

    @Value("${user.API_AMS}")
    private String usuario;
    @Value("${clave.API_AMS}")
    private String clave;
    @Value("${empresa.API_AMS}")
    private String empresa;

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        System.out.println("REQUEST POST LOGIN");
        AuthenticationRequest request = new AuthenticationRequest(usuario, clave, empresa);
        boolean success = authService.login(request);
        return new ResponseEntity<>( success? "Acceso Exitoso" : "Credenciales Erróneas", HttpStatus.OK);
    }
}
