package com.TestAPI_AMS.API_AMS.domain.dto.Utilidades;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Utils {
    public static Timestamp getPastDate(int nDays){
        LocalDate today = LocalDate.now();
        LocalDate fiveDaysBack = today.minusDays(5);
        LocalDateTime fiveDaysBackStart = fiveDaysBack.atStartOfDay();
        return Timestamp.valueOf(fiveDaysBackStart);
    }

    public static Integer calculateNoDays(Timestamp lastRead) {
        LocalDate lastReadDate = lastRead.toLocalDateTime().toLocalDate();
        LocalDate today = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(lastReadDate, today);
    }
}
