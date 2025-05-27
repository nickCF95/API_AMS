package com.TestAPI_AMS.API_AMS.domain.dto.Acciones.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Dispositivo {
    private String serial;
    private List<Peticion> listaPeticiones;
}
