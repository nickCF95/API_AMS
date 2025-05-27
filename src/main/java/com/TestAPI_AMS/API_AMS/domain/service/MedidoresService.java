package com.TestAPI_AMS.API_AMS.domain.service;

import com.TestAPI_AMS.API_AMS.custom.handlers.WebClientStatusCodeHandler;
import com.TestAPI_AMS.API_AMS.domain.dto.Aprovisionamiento.Request.Insert.DispositivoComunicacionI;
import com.TestAPI_AMS.API_AMS.domain.dto.Aprovisionamiento.Request.Insert.MedidorI;
import com.TestAPI_AMS.API_AMS.domain.dto.Aprovisionamiento.Response.Insert.DispositivoComunicacionResponseI;
import com.TestAPI_AMS.API_AMS.domain.dto.Medidores.Response.MedidorResponse;
import com.TestAPI_AMS.API_AMS.domain.dto.Medidores.Response.Medidor;
import com.TestAPI_AMS.API_AMS.domain.dto.Utilidades.TokenWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedidoresService {

    @Autowired
    private WebClient client;

    @Autowired
    private DataMongoAMSService dataMongoAMSService;


    private final TokenWrapper tokenWrapper;

    /*
    public MedidoresService(TokenWrapper tokenWrapper) {
        this.tokenWrapper = tokenWrapper;
    }
    */
    public MedidorResponse consultarAlgunos(String paginacion, String paginaActual){
        try {
            MedidorResponse getResponse = client
                    .get()
                    .uri(uriBuilder -> uriBuilder.path("/gestorActivos/medidor/")
                            .queryParam("estados", "1")
                            .queryParam("paginaActual", paginaActual)
                            .queryParam("paginacion", paginacion)
                            .build())
                    .headers(h -> h.setBearerAuth(tokenWrapper.getToken()))
                    .retrieve()
                    .bodyToMono(MedidorResponse.class)
                    .block();
            assert getResponse != null;
            return getResponse;
        } catch (Exception e) {
            // Log error for debugging
            System.err.println("Error fetching Todos los Medidores: " + e.getMessage());
            // Return a fallback or rethrow
            throw new RuntimeException("Failed to fetch Medidores: " + e.getMessage(), e);
        }
    }

    public List<Medidor> consultarTodos() {
        List<Medidor> allMeters = new ArrayList<>();
        boolean notAllCheck = true;
        String paginacion = "50", paginaActual = "1";
        MedidorResponse medidorResponse;
        try {
            do {
                medidorResponse = consultarAlgunos(paginacion, paginaActual);
                allMeters.addAll(medidorResponse.getLista());
                if ( (Integer.parseInt(paginaActual) * Integer.parseInt(paginacion) )< medidorResponse.getTotal()){
                    paginaActual = "" + (Integer.parseInt(paginaActual) + 1);
                } else {
                    notAllCheck = false;
                }
            } while (notAllCheck);
            return allMeters;
        } catch (Exception e) {
            // Log error for debugging
            System.err.println("Error fetching Todos los Medidores: " + e.getMessage());
            // Return a fallback or rethrow
            throw new RuntimeException("Failed to fetch Medidores: " + e.getMessage(), e);
        }
    }

    public Medidor consultarMedidor(String serial) {
        try {
            MedidorResponse getResponse = client
                    .get()
                    .uri(uriBuilder -> uriBuilder.path("/gestorActivos/medidor/")
                            .queryParam("estados", "1")
                            .queryParam("paginaActual", "1")
                            .queryParam("paginacion", "1")
                            .queryParam("serial", serial)
                            .build())
                    .headers(h -> h.setBearerAuth(tokenWrapper.getToken()))
                    .retrieve()
                    .bodyToMono(MedidorResponse.class)
                    .block();

            return getResponse.getLista().get(0);
        } catch (Exception e) {
            // Log error for debugging
            System.err.println("Error fetching MedidorI: " + e.getMessage());
            // Return a fallback or rethrow
            throw new RuntimeException("Failed to fetch MedidorI: " + e.getMessage(), e);
        }
    }

    public ResponseEntity<String> insertarMedidoresMasivo(List<MedidorI> medidores){
        try{
            for(MedidorI medidor : medidores){
                try{
                    insertarMedidor(medidor);
                } catch(Exception e) {
                    log.info("Error al insertar dispositivo Medidor: " + medidor.getSerial());
                    e.printStackTrace();
                }
            }
            log.info("Inserción de medidores masiva exitosa");
            return ResponseEntity.ok("Inserción masiva exitosa");
        } catch (Exception e) {
            log.info("Error al insertar medidores masivamente");
            log.info("Detalles: " + e.getMessage());
            return new ResponseEntity<>("Petición no exitosa", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> insertarMedidor(MedidorI medidor){
        //System.out.println("Token almacenado: " + tokenWrapper.getToken());
        try{
            DispositivoComunicacionResponseI response = client.post()
                    .uri("/gestorActivos/medidor/")
                    .headers(h -> h.setBearerAuth(tokenWrapper.getToken()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(medidor), MedidorI.class)
                    .retrieve()
                    .bodyToFlux(DispositivoComunicacionResponseI.class)
                    .blockFirst();
            if (response.getError() != null){
                log.info("Error al insertar medidor: " + medidor.getSerial());
                log.info("Detalles: " + response.getMessage());
                return ResponseEntity.badRequest().body(response.getMessage());
            }
            log.info("Inserción exitosa del medidor: " + medidor.getSerial());
            return ResponseEntity.ok(response.toString());
        } catch (WebClientResponseException wcrE) {
            log.info("Error al insertar medidor: " + medidor.getSerial());
            log.info("Detalles: " + wcrE.getResponseBodyAsString());
            return new ResponseEntity<>("Petición no exitosa", wcrE.getStatusCode());
        }
    }
}
