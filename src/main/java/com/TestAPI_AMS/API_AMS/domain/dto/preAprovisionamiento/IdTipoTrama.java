package com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento;

import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.Estado;

public class IdTipoTrama {
    int codigo_trama;
    Estado estado;

    public IdTipoTrama() {
    }

    public IdTipoTrama(int codigo_trama, Estado estado) {
        this.codigo_trama = codigo_trama;
        this.estado = estado;
    }

    public int getCodigo_trama() {
        return codigo_trama;
    }

    public void setCodigo_trama(int codigo_trama) {
        this.codigo_trama = codigo_trama;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
