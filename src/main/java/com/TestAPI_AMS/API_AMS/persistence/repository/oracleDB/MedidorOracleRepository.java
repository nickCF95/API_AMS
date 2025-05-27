package com.TestAPI_AMS.API_AMS.persistence.repository.oracleDB;

import com.TestAPI_AMS.API_AMS.persistence.entity.MedidorOracle;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface MedidorOracleRepository extends JpaRepository<MedidorOracle, String> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE MEDIDORES SET ULTIMA_LECTURA = :lastRead WHERE ID_MEDIDOR = :serial", nativeQuery = true)
    void updateLastRead(@Param("lastRead") Date lastRead, @Param("serial") String serial);

    @Query(value = "SELECT ULTIMA_LECTURA FROM MEDIDORES WHERE ID_MEDIDOR = :serial", nativeQuery = true)
    Date findUltimaLecturaByIdMedidor(@Param("serial") String serial);

    @Procedure
    void ACTUALIZA_FECHA_ULTIMA_LECTURA(@Param("SERIE") String serial);
}
