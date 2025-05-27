package com.TestAPI_AMS.API_AMS.domain.dto.Aprovisionamiento.Request.Insert;

import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadMedidorAsociada implements Serializable {
    private Integer estado;
    private String nombre_estado;
    private String serial_medidor;
    private String nombre_prop_tipo_disp;
    private String value;
}
