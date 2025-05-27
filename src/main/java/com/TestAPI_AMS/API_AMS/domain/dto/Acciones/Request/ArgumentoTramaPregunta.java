package com.TestAPI_AMS.API_AMS.domain.dto.Acciones.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ArgumentoTramaPregunta {
    private String etiqueta_trama_preg;
    private String valor;
}
