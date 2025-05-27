package com.TestAPI_AMS.API_AMS.domain.service;


import com.TestAPI_AMS.API_AMS.domain.dto.Login.Request.AuthenticationRequest;
import com.TestAPI_AMS.API_AMS.domain.dto.Login.Response.AuthenticationResponse;
import com.TestAPI_AMS.API_AMS.domain.dto.Utilidades.TokenWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${user.API_AMS}")
    private String usuario;
    @Value("${clave.API_AMS}")
    private String clave;
    @Value("${empresa.API_AMS}")
    private String empresa;

    private final WebClient client;
    private final TokenWrapper tokenWrapper;

    private AuthenticationResponse authResp;

    public boolean autoLogin(){
        AuthenticationRequest request = new AuthenticationRequest(usuario, clave, empresa);
        return login(request);
    }

    public boolean login(AuthenticationRequest authReq){
        try {
            authResp = client.post()
                                                    .uri("/gestorActivos/usuarios/login")
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .body(Mono.just(authReq), AuthenticationRequest.class)
                                                    .retrieve().bodyToMono(AuthenticationResponse.class)
                                                    .block();
            System.out.println("Token obtenido: " + authResp.getToken());
            tokenWrapper.setToken(authResp.getToken());
            return true;
        } catch (WebClientResponseException wcrE) {
            return false;
        }
    }
}
