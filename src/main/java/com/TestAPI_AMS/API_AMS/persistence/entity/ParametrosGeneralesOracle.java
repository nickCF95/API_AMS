package com.TestAPI_AMS.API_AMS.persistence.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "PARAMETROS_GENERALES")
public class ParametrosGeneralesOracle {

    @Id
    @Column(name = "PARAMETROS_GENERALES_ID")
    private int id;

    @Column(nullable = false)
    private String tipo;
    private String nombre;
    private String valor;
    @Column(name = "FECHA_CREA", columnDefinition = "DATE DEFAULT CURRENT_TIMESTAMP")
    private Timestamp fechaCreacion;
}
