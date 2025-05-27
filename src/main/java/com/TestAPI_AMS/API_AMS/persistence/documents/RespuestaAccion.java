package com.TestAPI_AMS.API_AMS.persistence.documents;


import com.TestAPI_AMS.API_AMS.domain.dto.ParsedDocuments.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "respuestaacciones")
public class RespuestaAccion {

    @Id
    private String id;

    private String registro;

    private Date fecha;

    // Getter and Setter for 'id'
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for 'registro'
    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    // Getter and Setter for 'fecha'
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ParsedDocument getParsedRegistro() {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            // Parse the JSON into a Map
            Map<String, Object> registroMap = objectMapper.readValue(this.getRegistro(), Map.class);

            ParsedRespuestaAccionesDocument response = new ParsedRespuestaAccionesDocument();

            // Set common fields
            response.setFechahorainicio(getDateFromMap((Map<String, Object>) registroMap.get("fechahorainicio")));
            response.setFechahorafin(getDateFromMap((Map<String, Object>) registroMap.get("fechahorafin")));
            response.setIdentificador((String) registroMap.get("identificador"));
            response.setConsulta((Integer) registroMap.get("consulta"));
            response.setMqttserial((String) registroMap.get("mqttserial"));
            response.setMqttBus((String) registroMap.get("mqttidbus"));
            response.setSuccess(true);
            // Handle "respuestas" if present
            if (registroMap.containsKey("respuestas")) {
                List<Map<String, Object>> respuestas = (List<Map<String, Object>>) registroMap.get("respuestas");
                checkNestedError(respuestas, response);
            }

            // Handle "error" if present
            if (registroMap.containsKey("error")) {
                Map<String, Object> errorMap = (Map<String, Object>) registroMap.get("error");
                response.setError(parseError(errorMap, false));
                response.setSuccess(false);
            }

            if (response.isSuccess()) {
                List<Map<String, Object>> respuestas = (List<Map<String, Object>>) registroMap.get("respuestas");

                // Initialize the result
                ParsedLecturasDocument processedRegistro = new ParsedLecturasDocument();

                // Loop through each "respuesta" (we assume only one is relevant here)
                if (!respuestas.isEmpty()) {
                    Map<String, Object> respuesta = respuestas.get(0);

                    // Extract "valor" array
                    List<Object> valor = (List<Object>) respuesta.get("valor");

                    // First element of "valor" is the list of labels
                    List<String> labels = (List<String>) valor.get(0);
                    processedRegistro.setLabels(labels);

                    // The remaining elements are the rows (lecturas)
                    List<DataLectura> lecturas = new ArrayList<>();
                    for (int i = 1; i < valor.size(); i++) {
                        List<Object> row = (List<Object>) valor.get(i);

                        // First value in the row is the timestamp
                        Map<String, Object> fechaMap = (Map<String, Object>) row.get(0);
                        Date fecha = new Date((Long) fechaMap.get("$date"));

                        // Remaining values are the data
                        List<Object> values = row.subList(1, row.size());

                        // Create a Lectura object
                        DataLectura lectura = new DataLectura();
                        lectura.setFecha(fecha);
                        lectura.setValues(values);

                        lecturas.add(lectura);
                    }
                    processedRegistro.setLecturas(lecturas);
                    response.setLecturasDocument(processedRegistro);
                }
            }

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse registro", e);
        }
    }

    private void checkNestedError(List<Map<String, Object>> respuestas, ParsedRespuestaAccionesDocument response) {
        for (Map<String, Object> respuestaMap : respuestas) {
            // Check for nested error
            if (respuestaMap.containsKey("error")) {
                Map<String, Object> nestedErrorMap = (Map<String, Object>) respuestaMap.get("error");
                response.setError(parseError(nestedErrorMap, true));
                response.setSuccess(false);
                return;
            }
        }
        response.setSuccess(true);
    }

    private ErrorInfo parseError(Map<String, Object> errorMap, boolean isNestedError) {
        ErrorInfo error = new ErrorInfo();
        if (isNestedError) {
            String errorMsg = (String) errorMap.get("string");
            error.setString("Nested Error: " + errorMsg);
        } else {
            Map<String, Object> json = (Map<String, Object>) errorMap.get("json");
            error.setString((String) json.get("error"));
        }
        error.setCodigo((Integer) errorMap.get("codigo"));
        return error;
    }

    private Date getDateFromMap(Map<String, Object> dateMap) {
        if (dateMap != null && dateMap.containsKey("$date")) {
            return new Date((Long) dateMap.get("$date"));
        }
        return null;
    }

    @Override
    public String toString() {
        return "RespuestaAccion{" +
                "id='" + id + '\'' +
                ", registro='" + registro + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
