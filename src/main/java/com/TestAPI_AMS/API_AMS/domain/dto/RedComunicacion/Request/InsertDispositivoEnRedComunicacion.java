package com.TestAPI_AMS.API_AMS.domain.dto.RedComunicacion.Request;

import com.TestAPI_AMS.API_AMS.domain.dto.RedComunicacion.Enumerables.NivelRed;
import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertDispositivoEnRedComunicacion implements Serializable {
    private Integer estado;
    private Integer id_tipo_red_com;
    private Integer nivel_red;
    private String serial_dispositivo;
    private String serial_padre;
    private Integer ultimo_nivel;
}
