package com.TestAPI_AMS.API_AMS.domain.service;

import com.TestAPI_AMS.API_AMS.persistence.documents.Evento;
import com.TestAPI_AMS.API_AMS.persistence.documents.LecturaMongo;
import com.TestAPI_AMS.API_AMS.persistence.documents.RespuestaAccion;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataMongoAMSService {

    private final MongoTemplate mongoTemplate;

    public List<LecturaMongo> getLecturas(String regex) {
        Query query = new Query();
        query.addCriteria(Criteria.where("registro").regex(escapeForRegex(regex)));
        List<LecturaMongo> dataLecturas = mongoTemplate.find(query, LecturaMongo.class, "lecturas");
        return dataLecturas;
    }

    public List<RespuestaAccion> getRespuestaAccion(String regex) {
        Query query = new Query();
        query.addCriteria(Criteria.where("registro").regex(escapeForRegex(regex)));
        List<RespuestaAccion> dataRespuestasAcciones = mongoTemplate.find(query, RespuestaAccion.class, "respuestaacciones");
        return dataRespuestasAcciones;
    }

    public List<Evento> getEvento(String regex) {
        Query query = new Query();
        query.addCriteria(Criteria.where("registro").regex(escapeForRegex(regex)));
        List<Evento> dataEventos = mongoTemplate.find(query, Evento.class, "eventos");
        return dataEventos;
    }

    public String escapeForRegex(String input) {
        return input.replace("\\", "\\\\")
                .replace(".", "\\.")
                .replace("*", "\\*")
                .replace("+", "\\+")
                .replace("?", "\\?")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("^", "\\^")
                .replace("$", "\\$")
                .replace("|", "\\|");
    }

    public String mapAction(Integer idTipoTrama) {
        return "";
    }
}
