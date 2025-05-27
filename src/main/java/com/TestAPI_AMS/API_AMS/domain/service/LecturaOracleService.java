package com.TestAPI_AMS.API_AMS.domain.service;
import com.TestAPI_AMS.API_AMS.persistence.entity.LecturaOracle;
import com.TestAPI_AMS.API_AMS.persistence.repository.oracleDB.LecturasOracleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LecturaOracleService {

    @Autowired
    private LecturasOracleRepository lecturaRepository;

    public void saveLectura(LecturaOracle lectura) {
        // Save to Oracle
        lecturaRepository.save(lectura);
    }
}

