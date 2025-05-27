package com.TestAPI_AMS.API_AMS.web.controllers;

import com.TestAPI_AMS.API_AMS.domain.dto.Acciones.Request.CuerpoAccionPOST;
import com.TestAPI_AMS.API_AMS.domain.service.AccionesService;
import com.TestAPI_AMS.API_AMS.domain.service.MedidoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medidores")
public class Medidores {

    @Autowired
    MedidoresService medServ;

    @Autowired
    AccionesService actServ;

}
