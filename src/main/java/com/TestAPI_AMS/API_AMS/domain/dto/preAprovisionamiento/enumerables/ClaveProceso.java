package com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables;

public enum ClaveProceso {
    AMR_LA("Lectura Automatica"),
    ORD_SERV("Orden Servicio");

    private String value;

    ClaveProceso(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
