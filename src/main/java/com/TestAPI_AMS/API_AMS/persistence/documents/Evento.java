package com.TestAPI_AMS.API_AMS.persistence.documents;

import com.TestAPI_AMS.API_AMS.domain.dto.ParsedDocuments.ParsedDocument;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Document(collection = "eventos")
public class Evento{
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
        return "Eventos: {" +
                "id='" + id + '\'' +
                ", registro='" + registro + '\'' +
                ", fecha=" + fecha +
                '}';
    }

    public ParsedDocument getParsedRegistro() {
        return null;
    }
}
