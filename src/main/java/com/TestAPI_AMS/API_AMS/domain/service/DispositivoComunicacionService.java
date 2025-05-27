package com.TestAPI_AMS.API_AMS.domain.service;

import com.TestAPI_AMS.API_AMS.domain.dto.Aprovisionamiento.Request.Insert.DispositivoComunicacionI;
import com.TestAPI_AMS.API_AMS.domain.dto.Aprovisionamiento.Response.Insert.DispositivoComunicacionResponseI;
import com.TestAPI_AMS.API_AMS.domain.dto.Utilidades.TokenWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispositivoComunicacionService {
    private final WebClient client;
    private final TokenWrapper tokenWrapper;

    public ResponseEntity<String> insertarDispositivoComunicacionMasivo(List<DispositivoComunicacionI> dispositivosComunicacion){
        try{
            for(DispositivoComunicacionI dispositivoComunicacion : dispositivosComunicacion){
                try {
                    insertarDispositivoComunicacion(dispositivoComunicacion);
                } catch (Exception e) {
                    log.info("Error al insertar dispositivo de Comunicación: " + dispositivoComunicacion.getSerial());
                    e.printStackTrace();
                }
            }
            log.info("Inserción de dispositivos de comunicación masiva exitosa");
            return ResponseEntity.ok("Inserción masiva exitosa");
        } catch (Exception e) {
            log.info("Error al insertar dispositivos de comunicación masivamente");
            log.info("Detalles: " + e.getMessage());
            return new ResponseEntity<>("Petición no exitosa", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> insertarDispositivoComunicacion(DispositivoComunicacionI dispositivoComunicacion){
            //System.out.println("Token almacenado: " + tokenWrapper.getToken());
            try{
                DispositivoComunicacionResponseI response = client.post()
                        .uri("/gestorActivos/dispositivoComunicacion/")
                        .headers(h -> h.setBearerAuth(tokenWrapper.getToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(dispositivoComunicacion), DispositivoComunicacionI.class)
                        .retrieve()
                        .bodyToFlux(DispositivoComunicacionResponseI.class)
                        .blockFirst();
                if (response.getError() != null){
                    log.info("Error al insertar dispositivo de comunicación: " + dispositivoComunicacion.getSerial());
                    log.info("Detalles: " + response.getMessage());
                    return ResponseEntity.badRequest().body(response.getMessage());
                }
                log.info("Inserción exitosa del dispositivo de comunicación: " + dispositivoComunicacion.getSerial());
                return ResponseEntity.ok(response.toString());
            } catch (WebClientResponseException wcrE) {
                log.info("Error al insertar dispositivo de comunicación: " + dispositivoComunicacion.getSerial());
                log.info("Detalles: " + wcrE.getResponseBodyAsString());
                return new ResponseEntity<>("Petición no exitosa", wcrE.getStatusCode());
            }
    }

}
