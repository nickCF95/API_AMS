package com.TestAPI_AMS.API_AMS.persistence.repository.oracleDB;

import com.TestAPI_AMS.API_AMS.persistence.entity.LecturaOracle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturasOracleRepository extends JpaRepository<LecturaOracle, Long> {
    // Custom query methods can be added here if needed
}
