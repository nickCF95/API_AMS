package com.TestAPI_AMS.API_AMS.domain.service;

import com.TestAPI_AMS.API_AMS.domain.dto.Acciones.Request.CuerpoAccionPOST;
import com.TestAPI_AMS.API_AMS.domain.dto.Acciones.Response.ActionResponse;
import com.TestAPI_AMS.API_AMS.domain.dto.Medidores.Response.Medidor;
import com.TestAPI_AMS.API_AMS.domain.dto.Utilidades.DataStorage;
import com.TestAPI_AMS.API_AMS.domain.dto.Utilidades.RedisDataStorage;
import com.TestAPI_AMS.API_AMS.domain.dto.Utilidades.TokenWrapper;
import com.TestAPI_AMS.API_AMS.domain.dto.Utilidades.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccionesService {
    private final WebClient client;
    private final TokenWrapper tokenWrapper;
    private final RedisDataStorage redisDataStorage;
    private final MedidoresService medidoresService;
    @Value("${readings.No_DAYS_BACK}")
    private Integer nDaysBack;

    public ResponseEntity<String> ejecutarAcciones(CuerpoAccionPOST actionParams) {
        System.out.println("Token almacenado: " + tokenWrapper.getToken());
        Integer idTipoTrama;
        try{
            List<ActionResponse> responseList = client.post()
                    .uri("/gestorActivos/acciones/")
                    .headers(h -> h.setBearerAuth(tokenWrapper.getToken()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(actionParams), CuerpoAccionPOST.class)
                    .retrieve()
                    .bodyToFlux(ActionResponse.class)
                    .collectList()
                    .block();

            assert responseList != null;
            Map<String, Timestamp> tiedMeters = redisDataStorage.getAllTiedMetersLastRead() ;
            for (ActionResponse response : responseList) {
                if (response.getMensaje_error_respuesta() != null ) {
                    log.info("Error:" + response.getMensaje_error_respuesta());
                    redisDataStorage.addUntiedMeter(response.getSerial());
                    continue;
                }
                String serial = response.getSerial();
                Integer idOrden = response.getId_orden_insertada();

                // Guardar orden en las estructuras
                redisDataStorage.addPendingOrder(idOrden, serial);

                // Consultar medidor
                Medidor medidor = medidoresService.consultarMedidor(serial);
                if (medidor!= null ) {
                    Integer medidorId = medidor.getId();
                    redisDataStorage.addSerialToId(serial, medidorId);
                    redisDataStorage.addIdToSerial(medidorId, serial);
                    if (!tiedMeters.containsKey(serial)){
                        redisDataStorage.addTiedMeterLastRead(serial, Utils.getPastDate(nDaysBack) );
                    }
                }
            }
            return ResponseEntity.ok(responseList.toString());
        } catch (WebClientResponseException wcrE) {
            return new ResponseEntity<>("Petición no exitosa", wcrE.getStatusCode());
        }
    }
}
