package com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento;

import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.Estado;
import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.TipoCampo;
import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.TipoDatoBus;

public class PropiedadTipoDispositivo {

    String nombre;
    String descripcion;
    TipoCampo tipo_campo;
    boolean obligatorio;
    Estado estado;
    String nombre_tipo_dispositivo;
    boolean enviar_amarre;
    TipoDatoBus tipo_dato_bus;

    public PropiedadTipoDispositivo() {
    }

    public PropiedadTipoDispositivo(String nombre, String descripcion, TipoCampo tipo_campo, boolean obligatorio, Estado estado, String nombre_tipo_dispositivo, boolean enviar_amarre, TipoDatoBus tipo_dato_bus) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo_campo = tipo_campo;
        this.obligatorio = obligatorio;
        this.estado = estado;
        this.nombre_tipo_dispositivo = nombre_tipo_dispositivo;
        this.enviar_amarre = enviar_amarre;
        this.tipo_dato_bus = tipo_dato_bus;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoCampo getTipo_campo() {
        return tipo_campo;
    }

    public void setTipo_campo(TipoCampo tipo_campo) {
        this.tipo_campo = tipo_campo;
    }

    public boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNombre_tipo_dispositivo() {
        return nombre_tipo_dispositivo;
    }

    public void setNombre_tipo_dispositivo(String nombre_tipo_dispositivo) {
        this.nombre_tipo_dispositivo = nombre_tipo_dispositivo;
    }

    public boolean isEnviar_amarre() {
        return enviar_amarre;
    }

    public void setEnviar_amarre(boolean enviar_amarre) {
        this.enviar_amarre = enviar_amarre;
    }

    public TipoDatoBus getTipo_dato_bus() {
        return tipo_dato_bus;
    }

    public void setTipo_dato_bus(TipoDatoBus tipo_dato_bus) {
        this.tipo_dato_bus = tipo_dato_bus;
    }
}
