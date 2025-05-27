package com.TestAPI_AMS.API_AMS.persistence.entity;

import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name = "LECTURAS") // Make sure the table name matches your Oracle table
public class LecturaOracle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LECTURA")
    private Long id;

    @Column(name = "ID_TIPO_LECTURA")
    private Integer idTipoLectura;

    @Column(name = "ID_MEDIDOR")
    private String idMedidor;

    @Column(name = "ID_TIPO_UNIDAD")
    private Integer idTipoUnidad;

    @Column(name = "VALOR")
    private Double valor;

    @Column(name = "SENTIDO")
    private Integer sentido;

    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "ENVIADO")
    private Integer enviado;

    @Column(name = "ID_TIPO_OBSERVACION")
    private Integer idTipoObservacion;

    @Column(name = "FASE")
    private Integer fase;

    @Column(name = "VALOR_HASH")
    private String valorHash;

    @Column(name = "MEASUREMENT_ID")
    private Integer measurementId;

    @Column(name = "MEASUREMENT_REFERENCE_ID")
    private Integer measurementReferenceId;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdTipoLectura() {
        return idTipoLectura;
    }

    public void setIdTipoLectura(Integer idTipoLectura) {
        this.idTipoLectura = idTipoLectura;
    }

    public String getIdMedidor() {
        return idMedidor;
    }

    public void setIdMedidor(String idMedidor) {
        this.idMedidor = idMedidor;
    }

    public Integer getIdTipoUnidad() {
        return idTipoUnidad;
    }

    public void setIdTipoUnidad(Integer idTipoUnidad) {
        this.idTipoUnidad = idTipoUnidad;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getSentido() {
        return sentido;
    }

    public void setSentido(Integer sentido) {
        this.sentido = sentido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getEnviado() {
        return enviado;
    }

    public void setEnviado(Integer enviado) {
        this.enviado = enviado;
    }

    public Integer getIdTipoObservacion() {
        return idTipoObservacion;
    }

    public void setIdTipoObservacion(Integer idTipoObservacion) {
        this.idTipoObservacion = idTipoObservacion;
    }

    public Integer getFase() {
        return fase;
    }

    public void setFase(Integer fase) {
        this.fase = fase;
    }

    public String getValorHash() {
        return valorHash;
    }

    public void setValorHash(String valorHash) {
        this.valorHash = valorHash;
    }

    public Integer getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(Integer measurementId) {
        this.measurementId = measurementId;
    }

    public Integer getMeasurementReferenceId() {
        return measurementReferenceId;
    }

    public void setMeasurementReferenceId(Integer measurementReferenceId) {
        this.measurementReferenceId = measurementReferenceId;
    }

    @Override
    public String toString() {
        return "LecturaOracle{" +
                "id=" + id +
                ", idTipoLectura=" + idTipoLectura +
                ", idMedidor='" + idMedidor + '\'' +
                ", idTipoUnidad=" + idTipoUnidad +
                ", valor=" + valor +
                ", sentido=" + sentido +
                ", fecha=" + fecha +
                ", enviado=" + enviado +
                ", idTipoObservacion=" + idTipoObservacion +
                ", fase=" + fase +
                ", valorHash='" + valorHash + '\'' +
                ", measurementId=" + measurementId +
                ", measurementReferenceId=" + measurementReferenceId +
                '}';
    }
}
