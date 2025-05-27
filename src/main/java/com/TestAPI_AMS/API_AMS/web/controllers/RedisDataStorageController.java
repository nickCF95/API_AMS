package com.TestAPI_AMS.API_AMS.web.controllers;

import com.TestAPI_AMS.API_AMS.domain.dto.Medidores.Response.Medidor;
import com.TestAPI_AMS.API_AMS.domain.dto.Utilidades.RedisDataStorage;
import com.TestAPI_AMS.API_AMS.domain.service.MedidoresService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/redis-datastorage")
public class RedisDataStorageController {

    @Autowired
    private MedidoresService medidoresService;

    @Autowired
    private RedisDataStorage redisDataStorage;

    @GetMapping("/all")
    public Map<String, Object> getAllDataStorageAttributes() {
        return Map.of(
                "serialToIdMap", redisDataStorage.getAllSerialToId(),
                "idToSerialMap", redisDataStorage.getAllIdToSerial(),
                "untiedMeters", redisDataStorage.getAllUntiedMeters(),
                "pendingOrders", redisDataStorage.getAllPendingOrders(),
                "pendingReadings", redisDataStorage.getAllPendingReadings()
        );
    }

    @PostMapping("/add-order")
    public String addPendingOrder(@RequestParam Integer orderId, @RequestParam String serial) {
        redisDataStorage.addPendingOrder(orderId, serial);
        // Consultar medidor
        Medidor medidor = medidoresService.consultarMedidor(serial);
        log.info("MedidorI encontrado:" + medidor);
        if (medidor!= null ) {
            Integer medidorId = medidor.getId();
            redisDataStorage.addSerialToId(serial, medidorId);
            redisDataStorage.addIdToSerial(medidorId, serial);
        }
        return "Orden pendiente agregada: OrderID = " + orderId + ", Serial = " + serial;
    }

    @PostMapping("/add-reading")
    public String addPendingReading(@RequestParam Integer medidorId, @RequestParam String serial) {
        redisDataStorage.addPendingReading(medidorId);
        redisDataStorage.addSerialToId(serial, medidorId);
        redisDataStorage.addIdToSerial(medidorId, serial);
        return "Lectura pendiente agregada: MedidorID = " + medidorId;
    }
}
