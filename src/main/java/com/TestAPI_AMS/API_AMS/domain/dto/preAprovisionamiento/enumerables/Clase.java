package com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables;

public enum Clase {
    PREGUNTA_TIPO_LECTURA(0),
    RESPUESTA(1),
    PREGUNTA_TIPO_ESCRITURA(2);

    private int value;

    private Clase(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
