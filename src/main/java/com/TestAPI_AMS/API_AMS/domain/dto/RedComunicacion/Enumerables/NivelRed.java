package com.TestAPI_AMS.API_AMS.domain.dto.RedComunicacion.Enumerables;

import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.Estado;

import java.util.HashMap;

public enum NivelRed {
    AGENTE(1),
    CONCENTRADOR(2),
    COLECTOR(3),
    MEDIDOR(4);

    private int value;
    private static HashMap<Integer, NivelRed> mappings;

    private NivelRed(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private static HashMap<Integer, NivelRed> getMappings() {
        synchronized (NivelRed.class) {
            if (mappings == null) {
                mappings = new HashMap<>();
            }
        }
        return mappings;
    }

    public static NivelRed forValue(final int idEnum) {
        System.out.println("IdEnum: " + idEnum);
        NivelRed nivelRed = getMappings().get(idEnum);
        System.out.println("Nivel Red: " + nivelRed == null ? "Not Found" : nivelRed.toString());
        return nivelRed;
    }
}
