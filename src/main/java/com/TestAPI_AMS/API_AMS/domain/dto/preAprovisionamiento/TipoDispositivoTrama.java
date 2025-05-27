package com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento;

import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.ClaveProceso;
import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.Estado;

import java.util.List;

public class TipoDispositivoTrama {
    ClaveProceso clave_proceso;
    Estado estado;
    String nombre_tipo_disp;
    List<IdTipoTrama> id_tipo_trama;

    public TipoDispositivoTrama() {
    }

    public TipoDispositivoTrama(ClaveProceso clave_proceso, Estado estado, String nombre_tipo_disp, List<IdTipoTrama> id_tipo_trama) {
        this.clave_proceso = clave_proceso;
        this.estado = estado;
        this.nombre_tipo_disp = nombre_tipo_disp;
        this.id_tipo_trama = id_tipo_trama;
    }

    public ClaveProceso getClave_proceso() {
        return clave_proceso;
    }

    public void setClave_proceso(ClaveProceso clave_proceso) {
        this.clave_proceso = clave_proceso;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNombre_tipo_disp() {
        return nombre_tipo_disp;
    }

    public void setNombre_tipo_disp(String nombre_tipo_disp) {
        this.nombre_tipo_disp = nombre_tipo_disp;
    }

    public List<IdTipoTrama> getId_tipo_trama() {
        return id_tipo_trama;
    }

    public void setId_tipo_trama(List<IdTipoTrama> id_tipo_trama) {
        this.id_tipo_trama = id_tipo_trama;
    }
}
