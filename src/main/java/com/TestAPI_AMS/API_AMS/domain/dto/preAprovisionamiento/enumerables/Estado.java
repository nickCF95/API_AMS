package com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables;

import java.util.HashMap;

public enum Estado {
    INACTIVO(0),
    ACTIVO(1),
    BORRADO(99);

    private int value;
    private static HashMap<Integer, Estado> mappings;

    private Estado(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private static HashMap<Integer, Estado> getMappings() {
        synchronized (Estado.class) {
            if (mappings == null) {
                mappings = new HashMap<>();
            }
        }
        return mappings;
    }

    public static Estado forValue(int idEnum) {
        System.out.println("IdEnum: " + idEnum);
        Estado estado = getMappings().get(idEnum);
        System.out.println("Estado: " + (estado == null ? "Not Found" : estado.toString()));
        return estado;
    }

    @Override
    public String toString() {
        return "Estado{" +
                "value=" + value +
                '}';
    }
}
