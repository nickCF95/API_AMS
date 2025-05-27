package com.TestAPI_AMS.API_AMS.domain.dto.ParsedDocuments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ParsedRespuestaAccionesDocument implements ParsedDocument {
    private Date fechahorainicio;
    private Date fechahorafin;
    private String identificador;
    private int consulta;
    private String mqttserial;
    private String mqttBus;
    private ParsedLecturasDocument lecturasDocument;
    private boolean success; // Indicates if the overall response is a success
    private ErrorInfo error;
}
