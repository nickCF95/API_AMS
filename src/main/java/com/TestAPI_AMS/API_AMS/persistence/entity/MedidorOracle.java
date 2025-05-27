package com.TestAPI_AMS.API_AMS.persistence.entity;


import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "MEDIDORES")
public class MedidorOracle {
    @Id
    @Column(name = "ID_MEDIDOR")
    private String idMedidor;

    @Column(name = "ID_TIPO_CONEXION")
    private Integer idTipoConexion;

    @Column(name = "ID_TIPO_MEDIDOR")
    private Integer idTipoMedidor;

    private String direccion;

    @Column(name = "ID_TIPO_ESTADO_ADMIN")
    private Integer idTipoEstadoAdmin;

    @Column(name = "ID_TIPO_ESTADO_ACTUAL")
    private Integer idTipoEstadoActual;

    @Column(name = "ID_CLIENTE")
    private String idCliente;

    private Boolean principal;

    @Column(name = "NIT_AGENTE")
    private String nitAgente;

    @Column(name = "ID_POSICION")
    private String idPosicion;

    private Integer multiplicador;

    private Boolean habilitado;

    @Column(name = "M_MACRO")
    private Boolean mMacro;

    @Column(name = "ULTIMA_LECTURA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaLectura;

    @Column(name = "TIEMPO_INTEGRACION")
    private String tiempoIntegracion;

    @Column(name = "IN_MDM")
    private Boolean inMdm;

    @Column(name = "PROGRAM_ID")
    private String programId;

    @Column(name = "ULTIMO_REPORTE")
    private Timestamp ultimoReporte;

    @Column(name = "CONEXION_RELE_EN_PROCESO")
    private Boolean conexionReleEnProceso;

    @Column(name = "DESCONEXION_RELE_EN_PROCESO")
    private Boolean desconexionReleEnProceso;
}
