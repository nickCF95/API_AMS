package com.TestAPI_AMS.API_AMS.web.controllers;

import com.TestAPI_AMS.API_AMS.domain.dto.Acciones.Request.CuerpoAccionPOST;
import com.TestAPI_AMS.API_AMS.domain.service.AccionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/acciones")
public class Acciones {

    @Autowired
    private AccionesService accionesService;

    @PostMapping("/execAction")
    public ResponseEntity<String> ejecutarAcciones(@RequestBody CuerpoAccionPOST actionParams) {
        System.out.println("Parámetros de Acciones capturados: " + actionParams);
        return accionesService.ejecutarAcciones(actionParams);
    }
}
