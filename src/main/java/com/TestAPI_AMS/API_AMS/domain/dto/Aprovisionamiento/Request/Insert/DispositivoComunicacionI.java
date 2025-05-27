package com.TestAPI_AMS.API_AMS.domain.dto.Aprovisionamiento.Request.Insert;

import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispositivoComunicacionI implements Serializable {
    private Integer estado;
    private String fecha_estado;
    private String fecha_instalacion;
    private String nombre_tipo_disp;
    private String serial;
    private List<PropiedadDispositivoComunicacionAsociada> propiedadesDispositivo;
}
