package com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento;

import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.Estado;
import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.TipoCampo;
import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.TipoDatoBus;

public class TipoTramaPregunta {
    String descripcion;
    String etiqueta;
    int codigo_trama;
    TipoCampo tipo_campo;
    boolean obligatorio;
    String valor_permitido;
    String valor_defecto;
    boolean cambiar_defecto;
    TipoDatoBus tipo_dato_bus;
    Estado estado;

    public TipoTramaPregunta() {
    }

    public TipoTramaPregunta(String descripcion, String etiqueta, int codigo_trama, TipoCampo tipo_campo, boolean obligatorio, String valor_permitido, String valor_defecto, boolean cambiar_defecto, TipoDatoBus tipo_dato_bus, Estado estado) {
        this.descripcion = descripcion;
        this.etiqueta = etiqueta;
        this.codigo_trama = codigo_trama;
        this.tipo_campo = tipo_campo;
        this.obligatorio = obligatorio;
        this.valor_permitido = valor_permitido;
        this.valor_defecto = valor_defecto;
        this.cambiar_defecto = cambiar_defecto;
        this.tipo_dato_bus = tipo_dato_bus;
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public int getCodigo_trama() {
        return codigo_trama;
    }

    public void setCodigo_trama(int codigo_trama) {
        this.codigo_trama = codigo_trama;
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

    public String getValor_permitido() {
        return valor_permitido;
    }

    public void setValor_permitido(String valor_permitido) {
        this.valor_permitido = valor_permitido;
    }

    public String getValor_defecto() {
        return valor_defecto;
    }

    public void setValor_defecto(String valor_defecto) {
        this.valor_defecto = valor_defecto;
    }

    public boolean isCambiar_defecto() {
        return cambiar_defecto;
    }

    public void setCambiar_defecto(boolean cambiar_defecto) {
        this.cambiar_defecto = cambiar_defecto;
    }

    public TipoDatoBus getTipo_dato_bus() {
        return tipo_dato_bus;
    }

    public void setTipo_dato_bus(TipoDatoBus tipo_dato_bus) {
        this.tipo_dato_bus = tipo_dato_bus;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
