package com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables;

public enum TipoPeticion {
    VALOR_SIMPLE(1),
    GRUPO_DE_VALORES(2),
    GRUPO_PERFIL_DE_CARGA(3),
    HISTORICO_DE_EVENTOS(4);

    private int value;

    private TipoPeticion(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
