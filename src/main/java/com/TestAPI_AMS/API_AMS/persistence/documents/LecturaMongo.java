package com.TestAPI_AMS.API_AMS.persistence.documents;

import com.TestAPI_AMS.API_AMS.domain.dto.ParsedDocuments.DataLectura;
import com.TestAPI_AMS.API_AMS.domain.dto.ParsedDocuments.ParsedLecturasDocument;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "lecturas")
public class LecturaMongo{

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

    @Override
    public String toString() {
        return "Lecturas: {" +
                "id='" + id + '\'' +
                ", registro='" + registro + '\'' +
                ", fecha=" + fecha +
                '}';
    }

    public ParsedLecturasDocument getParsedRegistro() {
        try {
            // Parse JSON string into a Map
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> registroMap = objectMapper.readValue(this.getRegistro(), Map.class);

            // Extract "fechahorainicio"
            Map<String, Object> fechahorainicioMap = (Map<String, Object>) registroMap.get("fechahorainicio");
            Date fechahorainicio = new Date((Long) fechahorainicioMap.get("$date"));

            String strId = (String) registroMap.get("identificador");
            Integer id = Integer.parseInt(strId.split("!")[0]);

            // Extract "respuestas" array
            List<Map<String, Object>> respuestas = (List<Map<String, Object>>) registroMap.get("respuestas");

            // Initialize the result
            ParsedLecturasDocument processedRegistro = new ParsedLecturasDocument();
            processedRegistro.setFechahorainicio(fechahorainicio);
            processedRegistro.setId(id);

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
            }

            return processedRegistro;

        } catch (Exception e) {
            throw new RuntimeException("Failed to process registro", e);
        }
    }
}
