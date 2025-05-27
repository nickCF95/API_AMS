package com.TestAPI_AMS.API_AMS.persistence.repository.oracleDB;

import com.TestAPI_AMS.API_AMS.persistence.entity.ParametrosGeneralesOracle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParametrosGeneralesRepository extends JpaRepository<ParametrosGeneralesOracle, Integer> {
    @Query(value = "SELECT VALOR FROM PARAMETROS_GENERALES WHERE PARAMETROS_GENERALES_ID = :id", nativeQuery = true)
    String findValorById(int id);
}
