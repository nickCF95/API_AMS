package com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento;

import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.*;

public class TipoTrama {
    Clase clase;
    String nombre;
    String descripcion;
    Estado estado;
    String clave_trama;
    TipoPeticion tipo_peticion;
    int codigo_trama;
    boolean convertir_numero;

    public TipoTrama() {
    }

    public TipoTrama(Clase clase, String nombre, String descripcion, Estado estado, String clave_trama, TipoPeticion tipo_peticion, int codigo_trama, boolean convertir_numero) {
        this.clase = clase;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.clave_trama = clave_trama;
        this.tipo_peticion = tipo_peticion;
        this.codigo_trama = codigo_trama;
        this.convertir_numero = convertir_numero;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getClave_trama() {
        return clave_trama;
    }

    public void setClave_trama(String clave_trama) {
        this.clave_trama = clave_trama;
    }

    public TipoPeticion getTipo_peticion() {
        return tipo_peticion;
    }

    public void setTipo_peticion(TipoPeticion tipo_peticion) {
        this.tipo_peticion = tipo_peticion;
    }

    public int getCodigo_trama() {
        return codigo_trama;
    }

    public void setCodigo_trama(int codigo_trama) {
        this.codigo_trama = codigo_trama;
    }

    public boolean isConvertir_numero() {
        return convertir_numero;
    }

    public void setConvertir_numero(boolean convertir_numero) {
        this.convertir_numero = convertir_numero;
    }
}
