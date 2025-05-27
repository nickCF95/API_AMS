package com.TestAPI_AMS.API_AMS.persistence.repository.mongoDB;

import com.TestAPI_AMS.API_AMS.persistence.documents.Evento;
import com.TestAPI_AMS.API_AMS.persistence.documents.LecturaMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventosRepository extends MongoRepository<Evento, String> {
    @Query("{'registro': {$regex: ?0, $options: 'i'}}")
    List<Evento> findByRegistroContaining(String word);
}
