package com.TestAPI_AMS.API_AMS.persistence.repository.mongoDB;

import com.TestAPI_AMS.API_AMS.persistence.documents.LecturaMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturasMongoRepository extends MongoRepository<LecturaMongo, String> {

    @Query(value = "{ 'registro': { $regex: ?0, $options: 'i' } }", sort = "{ 'fecha': -1 }")
    List<LecturaMongo> findTop1ByRegistroRegex(String regex);
}
