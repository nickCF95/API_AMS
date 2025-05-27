package com.TestAPI_AMS.API_AMS.domain.service;

import com.TestAPI_AMS.API_AMS.domain.dto.Acciones.Request.ArgumentoTramaPregunta;
import com.TestAPI_AMS.API_AMS.domain.dto.Acciones.Request.CuerpoAccionPOST;
import com.TestAPI_AMS.API_AMS.domain.dto.Acciones.Request.Dispositivo;
import com.TestAPI_AMS.API_AMS.domain.dto.Acciones.Request.Peticion;
import com.TestAPI_AMS.API_AMS.domain.dto.Login.Request.AuthenticationRequest;
import com.TestAPI_AMS.API_AMS.domain.dto.Medidores.Response.Medidor;
import com.TestAPI_AMS.API_AMS.domain.dto.ParsedDocuments.ParsedLecturasDocument;
import com.TestAPI_AMS.API_AMS.domain.dto.ParsedDocuments.DataLectura;
import com.TestAPI_AMS.API_AMS.domain.dto.ParsedDocuments.ParsedRespuestaAccionesDocument;
import com.TestAPI_AMS.API_AMS.domain.dto.Utilidades.DataStorage;
import com.TestAPI_AMS.API_AMS.domain.dto.Utilidades.RedisDataStorage;
import com.TestAPI_AMS.API_AMS.domain.dto.Utilidades.Utils;
import com.TestAPI_AMS.API_AMS.persistence.documents.LecturaMongo;
import com.TestAPI_AMS.API_AMS.persistence.documents.RespuestaAccion;
import com.TestAPI_AMS.API_AMS.persistence.entity.LecturaOracle;
import com.TestAPI_AMS.API_AMS.persistence.repository.mongoDB.RespuestaAccionesRepository;
import com.TestAPI_AMS.API_AMS.persistence.repository.mongoDB.LecturasMongoRepository;
import com.TestAPI_AMS.API_AMS.persistence.repository.oracleDB.LecturasOracleRepository;
import com.TestAPI_AMS.API_AMS.persistence.repository.oracleDB.MedidorOracleRepository;
import com.TestAPI_AMS.API_AMS.persistence.repository.oracleDB.ParametrosGeneralesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationNotSupportedException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
@EnableAsync
public class MongoPollingService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MedidoresService medidoresService;

    @Autowired
    private AccionesService accionesService;

    @Autowired
    private LecturasMongoRepository lecturasMongoRepository;

    @Autowired
    private LecturasOracleRepository lecturasOracleRepository;

    @Autowired
    private MedidorOracleRepository medidorOracleRepository;

    @Autowired
    private ParametrosGeneralesRepository parametrosGeneralesRepository;

    @Autowired
    private RespuestaAccionesRepository respuestaAccionesRepository;

    @Autowired
    private DataStorage dataStorage;

    @Autowired
    private RedisDataStorage redisDataStorage; // Use Redis-backed storage

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${user.API_AMS}")
    private String usuario;
    @Value("${clave.API_AMS}")
    private String clave;
    @Value("${empresa.API_AMS}")
    private String empresa;

    @Value("${readings.No_DAYS_BACK}")
    private Integer nDaysBack;

    @Async
    @Schedules(value = {
            @Scheduled(initialDelay = 30_000,
                    fixedDelay = Long.MAX_VALUE),
            @Scheduled(cron = "0 0 */2 * * *")
    })
    public void realizarPollingPerfilCarga() {
        log.info("Entrando Polling Perfil Carga");
        //log.info("Probando parámetro general de días: " + parametrosGeneralesRepository.findValorById(39));
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(usuario, clave, empresa);
        authenticationService.login(authenticationRequest);
        List<Medidor> medidores = medidoresService.consultarTodos();
        List<Dispositivo> dispositivos = new ArrayList<>();
        Dispositivo dispositivo;
        Map<Integer, String> pendingOrders;
        Set<String> untiedMeters;
        Map<String, Timestamp> tiedMeters;
        Timestamp lastRead;
        try{
            pendingOrders = redisDataStorage.getAllPendingOrders();
            untiedMeters = redisDataStorage.getAllUntiedMeters();
            tiedMeters = redisDataStorage.getAllTiedMetersLastRead();
        } catch (RedisSystemException exception) {
            log.info("Sin conexión con Redis.");
            return;
        }
        List<String> medidoresPendientes = new ArrayList<>();
        medidoresPendientes.addAll(pendingOrders.values());
        for(Medidor medidor : medidores){
            System.out.println("Medidor a chequear: " + medidor.getSerial());
            if(untiedMeters.contains(medidor.getSerial())) continue;
            if(medidoresPendientes.contains(medidor.getSerial())) continue;
            if(tiedMeters.containsKey(medidor.getSerial())){
                lastRead = tiedMeters.get(medidor.getSerial());
            } else {//Si no se ecuentra dentro de las tablas de Redis
                try{
                    //Primero busca en DB si es que el medidor se encuentra ahí y nos pasa el last read
                    lastRead = new Timestamp(medidorOracleRepository.findUltimaLecturaByIdMedidor(medidor.getSerial()).getTime());
                } catch (Exception e){
                    //Si no esta el medidor y por ende se lanza una excepción capturamos de la DB los días
                    lastRead = Utils.getPastDate(Integer.parseInt(parametrosGeneralesRepository.findValorById(39)));
                }
            }
            dispositivo = new Dispositivo();
            dispositivo.setSerial(medidor.getSerial());
            dispositivo.setListaPeticiones(getPeticiones(lastRead));
            dispositivos.add(dispositivo);
        }
        CuerpoAccionPOST cuerpoAccionPOST = new CuerpoAccionPOST();
        cuerpoAccionPOST.setListaDispositivos(dispositivos);
        accionesService.ejecutarAcciones(cuerpoAccionPOST);
    }

    private static List<Peticion> getPeticiones(Timestamp lastRead) {
        String nDiasStr = "" + Utils.calculateNoDays(lastRead);
        List<ArgumentoTramaPregunta> argumentoTramaPreguntaList = new ArrayList<>();
        ArgumentoTramaPregunta argumentoTramaPregunta = new ArgumentoTramaPregunta();
        argumentoTramaPregunta.setEtiqueta_trama_preg("dias");
        argumentoTramaPregunta.setValor(nDiasStr);
        argumentoTramaPreguntaList.add(argumentoTramaPregunta);
        List<Peticion> peticiones = new ArrayList<>();
        Peticion peticion = new Peticion();
        peticion.setCodigo_trama(222);
        peticion.setListaValores(argumentoTramaPreguntaList);
        peticiones.add(peticion);
        return peticiones;
    }

    @Async
    @Schedules(value = {
            @Scheduled(initialDelay = 15_000,
                    fixedDelay = Long.MAX_VALUE),
            @Scheduled(cron = "0 */10 * * * *")
    })
    public void realizarPollingRespuestaAcciones() {
        log.info("Entrando Polling Respuesta Acciones");
        Map<Integer, String> pendingOrders;
        try {
            pendingOrders = redisDataStorage.getAllPendingOrders();
        } catch (RedisSystemException exception) {
            log.info("Sin conexión con Redis.");
            return;
        }
        if (pendingOrders.isEmpty()) {
            log.info("Polling de RespuestaAcciones detenido: No hay órdenes pendientes.");
            return;
        }
        pendingOrders.forEach((orderId, serial) -> {
            String regex = "\"consulta\"\\s*:\\s*" + orderId;

            RespuestaAccion[] respuestas = respuestaAccionesRepository.findByRegistroContaining(regex);
            if (respuestas != null) {
                for (RespuestaAccion respuesta: respuestas){
                    ParsedRespuestaAccionesDocument parsedRespuestaAccionesDocument = (ParsedRespuestaAccionesDocument) respuesta.getParsedRegistro();
                    if (!parsedRespuestaAccionesDocument.isSuccess()) {
                        System.out.println("Error en la petición: \n\t" + parsedRespuestaAccionesDocument.getError().toString());
                    } else if(parsedRespuestaAccionesDocument.getLecturasDocument() != null) { //Lectura en Respuesta Acciones
                        System.out.println("Lecturas en Respuesta Acciones");
                        saveLecturasOracle(parsedRespuestaAccionesDocument.getLecturasDocument(), null, serial);
                    } else {
                        System.out.println("Orden exitosa: " + orderId);
                        Integer medidorId;
                        try {
                            medidorId = redisDataStorage.getIdBySerial(serial);
                        } catch (RedisSystemException exception) {
                            log.info("Sin conexión con Redis.");
                            return;
                        }
                        if (medidorId != null) {
                            redisDataStorage.addPendingReading(medidorId);
                        }
                    }
                    // Eliminar la orden de las pendientes
                    redisDataStorage.removePendingOrder(orderId);
                    respuestaAccionesRepository.delete(respuesta);
                }
            }
        });
    }

    @Async
    @Schedules(value = {
            @Scheduled(initialDelay = 15_000,
                    fixedDelay = Long.MAX_VALUE),
            @Scheduled(cron = "0 0 */1 * * *")
    })
    public void realizarPollingLecturas() {
        log.info("Entrando Polling Lecturas");
        List<Integer> pendingReadings = redisDataStorage.getAllPendingReadings();
        if (pendingReadings.isEmpty()) {
            log.info("Polling de Lecturas detenido: No hay lecturas pendientes.");
            return;
        }
        int idx = 0;
        for (Integer medidorId : pendingReadings) {
            String identificador = medidorId + "!smp_plus";
            log.info("Identificador a buscar: " + identificador);
            String regex = "\"identificador\"\\s*:\\s*\"" + identificador + "\"";

            List<LecturaMongo> lecturas = lecturasMongoRepository.findTop1ByRegistroRegex(regex);
            log.info("Lecturas: " + lecturas);
            if (lecturas != null && !lecturas.isEmpty()) {
                LecturaMongo lectura = lecturas.get(0);
                ParsedLecturasDocument parsedLecturasDoc = lectura.getParsedRegistro();
                saveLecturasOracle(parsedLecturasDoc, medidorId, "");
                redisDataStorage.removePendingReading(idx);
                lecturasMongoRepository.delete(lectura);
            } else {
                log.info("Lectura no encontrada");
            }
            idx ++;
        }
    }

    private void saveLecturasOracle(ParsedLecturasDocument parsedLecturasDoc, Integer medidorId, String serialMed) {
        List<LecturaOracle> lecturasToSave = mapMongoToRegistroResponse(parsedLecturasDoc, medidorId, serialMed);
        int batchSize;
        try{
            batchSize = Integer.parseInt(parametrosGeneralesRepository.findValorById(40));
        } catch (Exception e){
            batchSize = 250;
        }
        if (!lecturasToSave.isEmpty()){
            log.info("Lecturas Parseadas: " + lecturasToSave);
            String mergeSQL = "MERGE INTO LECTURAS a USING " +
                    "(SELECT * FROM " +
                    "(SELECT * FROM " +
                    "(SELECT FECHA, ID_MEDIDOR, ID_TIPO_UNIDAD, SENTIDO, FASE FROM LECTURAS " +
                    "WHERE ID_MEDIDOR = ? AND FECHA = ? AND ID_TIPO_UNIDAD = ? AND SENTIDO = ? AND FASE = ? " +
                    "UNION SELECT NULL FECHA, NULL ID_MEDIDOR, 0 ID_TIPO_UNIDAD, 0 SENTIDO, 0 FASE FROM DUAL) " +
                    "ORDER BY FECHA ASC) " +
                    "WHERE ROWNUM = 1 ) b " +
                    "ON (a.FECHA = b.FECHA AND a.ID_MEDIDOR = b.ID_MEDIDOR AND a.ID_TIPO_UNIDAD = b.ID_TIPO_UNIDAD AND a.SENTIDO = b.SENTIDO AND a.FASE = b.FASE) " +
                    "WHEN MATCHED THEN " +
                    "UPDATE SET a.VALOR = ? " +
                    "WHEN NOT MATCHED THEN " +
                    "INSERT (ID_TIPO_LECTURA, ID_MEDIDOR, ID_TIPO_UNIDAD, VALOR, SENTIDO, FECHA, ENVIADO, ID_TIPO_OBSERVACION, FASE, VALOR_HASH) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            int[][] lecsInserted = jdbcTemplate.batchUpdate(mergeSQL,
                    lecturasToSave,
                    batchSize,
                    new ParameterizedPreparedStatementSetter<LecturaOracle>() {
                        @Override
                        public void setValues(PreparedStatement ps, LecturaOracle argument) throws SQLException {
                            ps.setString(1, argument.getIdMedidor());
                            ps.setDate(2, new java.sql.Date(argument.getFecha().getTime()));
                            ps.setInt(3, argument.getIdTipoUnidad());
                            ps.setInt(4, argument.getSentido());
                            ps.setInt(5, argument.getFase());
                            // WHEN MATCHED Clause
                            ps.setDouble(6, argument.getValor());

                            // WHEN NOT MATCHED Clause
                            ps.setInt(7, argument.getIdTipoLectura());
                            ps.setString(8, argument.getIdMedidor());
                            ps.setInt(9, argument.getIdTipoUnidad());
                            ps.setDouble(10, argument.getValor());
                            ps.setInt(11, argument.getSentido());
                            ps.setDate(12, new java.sql.Date(argument.getFecha().getTime()));
                            ps.setInt(13, argument.getEnviado());
                            ps.setInt(14, argument.getIdTipoObservacion());
                            ps.setInt(15, argument.getFase());
                            ps.setString(16, argument.getValorHash());
                        }
                    });
            //lecturasOracleRepository.saveAll(lecturasToSave);
            medidorOracleRepository.ACTUALIZA_FECHA_ULTIMA_LECTURA(serialMed);
            //medidorOracleRepository.updateLastRead(lastRead.getFecha(), lastRead.getIdMedidor());
            redisDataStorage.addTiedMeterLastRead(serialMed, new Timestamp(medidorOracleRepository.findUltimaLecturaByIdMedidor(serialMed).getTime()));
        }
    }

    private List<LecturaOracle> mapMongoToRegistroResponse(ParsedLecturasDocument parsedLecturasDoc, Integer medidorId, String serial) {

        log.info("Documento lecturas parseado: " + parsedLecturasDoc);
        List<String> channels = parsedLecturasDoc.getLabels();
        List<DataLectura> lecturas = parsedLecturasDoc.getLecturas();
        List<LecturaOracle> lecturasToSave = new ArrayList<>();
        LecturaOracle lecturaOracle;
        for (DataLectura lec : lecturas) {
            int counter = 0;
            String channelLabel;
            String serialMed = "";
            for(Object value : lec.getValues()){
                if (counter == 0) {
                    counter ++;
                    continue;
                }
                channelLabel = channels.get(counter + 1);
                Double floatValue = Double.parseDouble(proccessValueLec(value));
                lecturaOracle = new LecturaOracle();
                lecturaOracle.setId(1L);
                lecturaOracle.setIdTipoLectura(1);
                if(serial == null){
                    try {
                        serialMed = redisDataStorage.getSerialById(medidorId);
                    } catch (RedisSystemException exception) {
                        log.info("Sin conexión con Redis.");
                        return lecturasToSave;
                    }
                } else {
                    if(serial.isEmpty()){
                        try{
                            serialMed = redisDataStorage.getSerialById(medidorId);
                        } catch (RedisSystemException exception) {
                            log.info("Sin conexión con Redis.");
                            return lecturasToSave;
                        }
                    } else {
                        serialMed = serial;
                    }
                }

                lecturaOracle.setIdMedidor( serialMed );//Rutina para buscarlo
                /*
                Con las unidades también se debería hacer la consulta inicial en DB de dicha tabla sacar todo lo que haya
                guardarlo o mantenerlo en sesión para saber cada registro de lectura que código de unidad se le asigna
                 */
                lecturaOracle.setIdTipoUnidad(dataStorage.getUnityAMS(channelLabel)[0]);//Depende del canal
                lecturaOracle.setValor(floatValue);
                lecturaOracle.setSentido(dataStorage.getUnityAMS(channelLabel)[1]); //Depende del canal
                lecturaOracle.setFecha(lec.getFecha());
                lecturaOracle.setEnviado(1);
                lecturaOracle.setIdTipoObservacion(1);
                lecturaOracle.setFase(dataStorage.getUnityAMS(channelLabel)[2]);
                lecturaOracle.setValorHash(UtilitiesService.calcularHash(serialMed+lec.getFecha()+lecturaOracle.getIdTipoUnidad()+lecturaOracle.getSentido()+lecturaOracle.getFase()));
                lecturasToSave.add(lecturaOracle);
                counter ++;
            }
        }
        return lecturasToSave;
    }

    private String proccessValueLec(Object value) {
        String valueStr = "";
        if (value.getClass() == String.class) {
            valueStr = (String) value;
            valueStr.trim().replace("\"", "");
        } else if(value.getClass() == Double.class){
            valueStr = ""+value;
        }
        return valueStr;
    }
}
