package com.TestAPI_AMS.API_AMS.persistence.repository.mongoDB;

import com.TestAPI_AMS.API_AMS.persistence.documents.LecturaMongo;
import com.TestAPI_AMS.API_AMS.persistence.documents.RespuestaAccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaAccionesRepository extends MongoRepository<RespuestaAccion, String> {
    @Query("{'registro': {$regex: ?0, $options: 'i'}}")
    RespuestaAccion[] findByRegistroContaining(String word);
}
