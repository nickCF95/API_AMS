package com.TestAPI_AMS.API_AMS.domain.dto.Acciones.Response;

import com.TestAPI_AMS.API_AMS.domain.dto.Acciones.Request.ArgumentoTramaPregunta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Peticion {
    private Integer id_tipo_trama;
    private Integer codigo_trama;
    private String accion;
    private String valor;
    private List<ArgumentoTramaPregunta> listaValores;
    private Boolean error;
    private String mensaje_error;
}
