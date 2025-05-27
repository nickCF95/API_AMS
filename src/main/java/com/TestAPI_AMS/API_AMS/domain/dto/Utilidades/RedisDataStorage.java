package com.TestAPI_AMS.API_AMS.domain.dto.Utilidades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RedisDataStorage {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String SERIAL_TO_ID_KEY = "serialToId";
    private static final String ID_TO_SERIAL_KEY = "idToSerial";
    private static final String UNTIED_METERS_KEY = "untiedMeters";
    private static final String TIED_METERS_LAST_READ = "tiedMetersLastRead";
    private static final String PENDING_ORDERS_KEY = "pendingOrders";
    private static final String PENDING_READINGS_KEY = "pendingReadings";

    // Serial -> ID
    public void addSerialToId(String serial, Integer id) {
        redisTemplate.opsForHash().put(SERIAL_TO_ID_KEY, serial, id);
    }

    // ID -> Serial
    public void addIdToSerial(Integer id, String serial) {
        redisTemplate.opsForHash().put(ID_TO_SERIAL_KEY, id, serial);
    }

    public Integer getIdBySerial(String serial) {
        return (Integer) redisTemplate.opsForHash().get(SERIAL_TO_ID_KEY, serial);
    }

    public String getSerialById(Integer id) {
        return (String) redisTemplate.opsForHash().get(ID_TO_SERIAL_KEY, id);
    }

    public Map<String, Integer> getAllSerialToId() {
        return redisTemplate.opsForHash().entries(SERIAL_TO_ID_KEY).entrySet().stream()
                .collect(Collectors.toMap(e -> (String) e.getKey(), e -> (Integer) e.getValue()));
    }

    public Map<Integer, String> getAllIdToSerial() {
        return redisTemplate.opsForHash().entries(ID_TO_SERIAL_KEY).entrySet().stream()
                .collect(Collectors.toMap(e -> (Integer) e.getKey(), e -> (String) e.getValue()));
    }

    //Untied Meters
    public void addUntiedMeter(String serialMedidor) {
        redisTemplate.opsForSet().add(UNTIED_METERS_KEY, serialMedidor);
    }

    public void removeUntiedMeter(String serialMedidor) {
        redisTemplate.opsForSet().remove(UNTIED_METERS_KEY, serialMedidor);
    }

    public Set<String> getAllUntiedMeters(){
        return redisTemplate.opsForSet().members(UNTIED_METERS_KEY).stream()
                .map(m -> (String) m)
                .collect(Collectors.toSet());
    }

    //Tied Meters Last Read
    public void addTiedMeterLastRead(String serial, Timestamp lastRead){
        redisTemplate.opsForHash().put(TIED_METERS_LAST_READ, serial, lastRead);
    }

    public Map<String, Timestamp> getAllTiedMetersLastRead(){
        return  redisTemplate.opsForHash().entries(TIED_METERS_LAST_READ).entrySet().stream()
                .collect(Collectors.toMap(e -> (String) e.getKey(), e -> (Timestamp) e.getValue()));
    }



    // Pending Orders
    public void addPendingOrder(Integer orderId, String serial) {
        redisTemplate.opsForHash().put(PENDING_ORDERS_KEY, orderId, serial);
    }

    public void removePendingOrder(Integer orderId) {
        redisTemplate.opsForHash().delete(PENDING_ORDERS_KEY, orderId);
    }

    public Map<Integer, String> getAllPendingOrders() {
        return redisTemplate.opsForHash().entries(PENDING_ORDERS_KEY).entrySet().stream()
                .collect(Collectors.toMap(e -> (Integer) e.getKey(), e -> (String) e.getValue()));
    }

    // Pending Readings
    public void addPendingReading(Integer medidorId) {
        redisTemplate.opsForList().rightPush(PENDING_READINGS_KEY, medidorId);
    }

    public void removePendingReading(Integer medidorId) {
        redisTemplate.opsForList().remove(PENDING_READINGS_KEY, 1, medidorId);
    }

    public List<Integer> getAllPendingReadings() {
        return redisTemplate.opsForList().range(PENDING_READINGS_KEY, 0, -1).stream()
                .map(e -> (Integer) e)
                .collect(Collectors.toList());
    }

    public void clearData() {
        redisTemplate.delete(ID_TO_SERIAL_KEY);
        redisTemplate.delete(SERIAL_TO_ID_KEY);
        redisTemplate.delete(UNTIED_METERS_KEY);
        redisTemplate.delete(TIED_METERS_LAST_READ);
        redisTemplate.delete(PENDING_ORDERS_KEY);
        redisTemplate.delete(PENDING_READINGS_KEY);
    }
}