package com.TestAPI_AMS.API_AMS.domain.service;

import com.TestAPI_AMS.API_AMS.domain.dto.Aprovisionamiento.Request.Insert.DispositivoComunicacionI;
import com.TestAPI_AMS.API_AMS.domain.dto.Aprovisionamiento.Request.Insert.MedidorI;
import com.TestAPI_AMS.API_AMS.domain.dto.Aprovisionamiento.Request.Insert.PropiedadDispositivoComunicacionAsociada;
import com.TestAPI_AMS.API_AMS.domain.dto.Aprovisionamiento.Request.Insert.PropiedadMedidorAsociada;
import com.TestAPI_AMS.API_AMS.domain.dto.RedComunicacion.Enumerables.NivelRed;
import com.TestAPI_AMS.API_AMS.domain.dto.RedComunicacion.Request.InsertDispositivoEnRedComunicacion;
import com.TestAPI_AMS.API_AMS.domain.dto.preAprovisionamiento.enumerables.Estado;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class AprovisionamientoService {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private DispositivoComunicacionService dispositivoComunicacionService;

    @Autowired
    private MedidoresService medidoresService;

    @Autowired
    private RedComunicacionService redComunicacionService;

    public void aprovisionarMasivo(Workbook wb) {
        authenticationService.autoLogin();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        Sheet concentradores = wb.getSheetAt(0);
        Iterator<Row> rowIt = concentradores.rowIterator();
        List<DispositivoComunicacionI> concentradoresObj = new ArrayList<>();
        List<DispositivoComunicacionI> colectoresObj = new ArrayList<>();
        List<MedidorI> medidoresObj = new ArrayList<>();
        List<InsertDispositivoEnRedComunicacion> dispositivosEnRedObj = new ArrayList<>();
        DispositivoComunicacionI concentrador = new DispositivoComunicacionI();
        concentrador.setEstado(Estado.ACTIVO.getValue());
        concentrador.setFecha_estado(sdf.format(new Date()));
        concentrador.setFecha_instalacion(sdf.format(new Date()));
        concentrador.setNombre_tipo_disp("CONCENTRADOR");
        List<PropiedadDispositivoComunicacionAsociada> listaPropiedades = new ArrayList<>();
        PropiedadDispositivoComunicacionAsociada TOPICO_ORDENES_MQTT = new PropiedadDispositivoComunicacionAsociada();
        TOPICO_ORDENES_MQTT.setEstado(Estado.ACTIVO.getValue());
        TOPICO_ORDENES_MQTT.setNombre_prop_disp("TOPICO_ORDENES_MQTT");
        TOPICO_ORDENES_MQTT.setValor("smarti/ordenes/");
        listaPropiedades.add(TOPICO_ORDENES_MQTT);
        PropiedadDispositivoComunicacionAsociada TOPICO_AMARRE_MQTT = new PropiedadDispositivoComunicacionAsociada();
        TOPICO_AMARRE_MQTT.setEstado(Estado.ACTIVO.getValue());
        TOPICO_AMARRE_MQTT.setNombre_prop_disp("TOPICO_AMARRE_MQTT");
        TOPICO_AMARRE_MQTT.setValor("smarti/configuracion/");
        listaPropiedades.add(TOPICO_AMARRE_MQTT);
        PropiedadDispositivoComunicacionAsociada TOPICO_EVENTOS_MQTT = new PropiedadDispositivoComunicacionAsociada();
        TOPICO_EVENTOS_MQTT.setEstado(Estado.ACTIVO.getValue());
        TOPICO_EVENTOS_MQTT.setNombre_prop_disp("TOPICO_EVENTOS_MQTT");
        TOPICO_EVENTOS_MQTT.setValor("smarti/eventos/");
        listaPropiedades.add(TOPICO_EVENTOS_MQTT);
        PropiedadDispositivoComunicacionAsociada TOPICO_MQTT = new PropiedadDispositivoComunicacionAsociada();
        TOPICO_MQTT.setEstado(Estado.ACTIVO.getValue());
        TOPICO_MQTT.setNombre_prop_disp("TOPICO_MQTT");
        TOPICO_MQTT.setValor("smarti/lectura_automatica/");
        listaPropiedades.add(TOPICO_MQTT);
        PropiedadDispositivoComunicacionAsociada PROCESO_LECTURA_MQTT = new PropiedadDispositivoComunicacionAsociada();
        PROCESO_LECTURA_MQTT.setEstado(Estado.ACTIVO.getValue());
        PROCESO_LECTURA_MQTT.setNombre_prop_disp("PROCESO_LECTURA_MQTT");
        PROCESO_LECTURA_MQTT.setValor("ENVIO_CONC");
        listaPropiedades.add(PROCESO_LECTURA_MQTT);
        PropiedadDispositivoComunicacionAsociada USUARIO_BROKER = new PropiedadDispositivoComunicacionAsociada();
        USUARIO_BROKER.setEstado(Estado.ACTIVO.getValue());
        USUARIO_BROKER.setNombre_prop_disp("USUARIO_BROKER");
        USUARIO_BROKER.setValor("mqtt-smarti-user");
        listaPropiedades.add(USUARIO_BROKER);
        PropiedadDispositivoComunicacionAsociada IMPORTAR_BBDD_LECTURA = new PropiedadDispositivoComunicacionAsociada();
        IMPORTAR_BBDD_LECTURA.setEstado(Estado.ACTIVO.getValue());
        IMPORTAR_BBDD_LECTURA.setNombre_prop_disp("IMPORTAR_BBDD_LECTURA");
        IMPORTAR_BBDD_LECTURA.setValor("1");
        listaPropiedades.add(IMPORTAR_BBDD_LECTURA);
        PropiedadDispositivoComunicacionAsociada PROCESO_ORDEN_MQTT = new PropiedadDispositivoComunicacionAsociada();
        PROCESO_ORDEN_MQTT.setEstado(Estado.ACTIVO.getValue());
        PROCESO_ORDEN_MQTT.setNombre_prop_disp("PROCESO_ORDEN_MQTT");
        PROCESO_ORDEN_MQTT.setValor("ENVIO_ORDEN");
        listaPropiedades.add(PROCESO_ORDEN_MQTT);
        PropiedadDispositivoComunicacionAsociada IP_BROKER = new PropiedadDispositivoComunicacionAsociada();
        IP_BROKER.setEstado(Estado.ACTIVO.getValue());
        IP_BROKER.setNombre_prop_disp("IP_BROKER");
        listaPropiedades.add(IP_BROKER);
        PropiedadDispositivoComunicacionAsociada IP = new PropiedadDispositivoComunicacionAsociada();
        IP.setEstado(Estado.ACTIVO.getValue());
        IP.setNombre_prop_disp("IP");
        listaPropiedades.add(IP);
        PropiedadDispositivoComunicacionAsociada PUERTO_BROKER = new PropiedadDispositivoComunicacionAsociada();
        PUERTO_BROKER.setEstado(Estado.ACTIVO.getValue());
        PUERTO_BROKER.setNombre_prop_disp("PUERTO_BROKER");
        listaPropiedades.add(PUERTO_BROKER);
        PropiedadDispositivoComunicacionAsociada CLAVE_BROKER = new PropiedadDispositivoComunicacionAsociada();
        CLAVE_BROKER.setEstado(Estado.ACTIVO.getValue());
        CLAVE_BROKER.setNombre_prop_disp("CLAVE_BROKER");
        listaPropiedades.add(CLAVE_BROKER);
        List<PropiedadDispositivoComunicacionAsociada> innerPropertiesList;
        InsertDispositivoEnRedComunicacion concentradorEnRed;
        // iterating rows..
        while (rowIt.hasNext()) {
            Row currentRow = rowIt.next();
            if (currentRow.getRowNum() == 0) {
                continue;
            }
            innerPropertiesList = new ArrayList<>();
            concentradorEnRed = new InsertDispositivoEnRedComunicacion();
            PropiedadDispositivoComunicacionAsociada innerProperty;
            String serialConcentrador = getContent(currentRow.getCell(0));
            concentradorEnRed.setEstado(Estado.ACTIVO.getValue());
            concentradorEnRed.setId_tipo_red_com(22);
            concentradorEnRed.setNivel_red(NivelRed.CONCENTRADOR.getValue());
            concentradorEnRed.setSerial_padre("33.342.234.12");
            concentradorEnRed.setSerial_dispositivo(serialConcentrador);
            concentradorEnRed.setUltimo_nivel(0);
            log.info("Concentrador en Red {}, fila {}", concentradorEnRed, currentRow.getRowNum() + 1);
            dispositivosEnRedObj.add((InsertDispositivoEnRedComunicacion)  SerializationUtils.clone(concentradorEnRed));
            concentrador.setSerial(serialConcentrador);
            for(PropiedadDispositivoComunicacionAsociada property : listaPropiedades) {
                property.setSerial_disp_com(serialConcentrador);
            }
            String ipBroker = getContent(currentRow.getCell(1));
            innerProperty = listaPropiedades.get(listaPropiedades.size() - 4);
            if(ipBroker != null && !ipBroker.isEmpty()) {
                innerProperty.setValor(ipBroker);
            } else {
                innerProperty.setValor("192.168.1.120");
            }
            String ip = getContent(currentRow.getCell(2));
            if(ip != null && !ip.isEmpty()) {
                innerProperty = listaPropiedades.get(listaPropiedades.size() - 3);
                innerProperty.setValor(ip);
            }
            String puertoBroker = getContent(currentRow.getCell(3));
            innerProperty = listaPropiedades.get(listaPropiedades.size() -2);
            if(puertoBroker != null && !puertoBroker.isEmpty()){
                innerProperty.setValor(puertoBroker);
            } else {
                innerProperty.setValor("1883");
            }
            String claveBroker = getContent(currentRow.getCell(4));
            innerProperty = listaPropiedades.get(listaPropiedades.size() -1);
            if(claveBroker != null && !claveBroker.isEmpty()){
                innerProperty.setValor(claveBroker);
            } else {
                innerProperty.setValor("Un10nUs3r2020*34");
            }
            innerPropertiesList.addAll(listaPropiedades);
            concentrador.setPropiedadesDispositivo(innerPropertiesList);
            log.info("Concentrador {} en la fila {}", concentrador, currentRow.getRowNum() + 1);
            concentradoresObj.add((DispositivoComunicacionI) SerializationUtils.clone(concentrador));
        }

        Sheet colectores = wb.getSheetAt(1);
        rowIt = colectores.rowIterator();
        DispositivoComunicacionI colector = new DispositivoComunicacionI();
        colector.setEstado(Estado.ACTIVO.getValue());
        colector.setFecha_estado(sdf.format(new Date()));
        colector.setFecha_instalacion(sdf.format(new Date()));
        colector.setNombre_tipo_disp("COLECTOR V2");
        List<PropiedadDispositivoComunicacionAsociada> listaPropiedadesColector = new ArrayList<>();
        PropiedadDispositivoComunicacionAsociada mac64 = new PropiedadDispositivoComunicacionAsociada();
        mac64.setEstado(Estado.ACTIVO.getValue());
        mac64.setNombre_prop_disp("mac64");
        listaPropiedadesColector.add(mac64);
        InsertDispositivoEnRedComunicacion colectorEnRed;
        // iterating rows..
        while (rowIt.hasNext()) {
            Row currentRow = rowIt.next();
            if (currentRow.getRowNum() == 0) {
                continue;
            }
            innerPropertiesList = new ArrayList<>();
            colectorEnRed = new InsertDispositivoEnRedComunicacion();
            String serialColector = "0013A200" + getContent(currentRow.getCell(0));
            colectorEnRed.setEstado(Estado.ACTIVO.getValue());
            colectorEnRed.setId_tipo_red_com(22);
            colectorEnRed.setNivel_red(NivelRed.COLECTOR.getValue());
            colectorEnRed.setSerial_padre(getContent(currentRow.getCell(1)));
            colectorEnRed.setSerial_dispositivo(serialColector);
            colectorEnRed.setUltimo_nivel(0);
            log.info("Colector en Red {}, fila {}", colectorEnRed, currentRow.getRowNum() + 1);
            dispositivosEnRedObj.add((InsertDispositivoEnRedComunicacion) SerializationUtils.clone(colectorEnRed));
            colector.setSerial(serialColector);
            for(PropiedadDispositivoComunicacionAsociada property: listaPropiedadesColector) {
                property.setSerial_disp_com(serialColector);
                property.setValor(serialColector);
            }
            innerPropertiesList.addAll(listaPropiedadesColector);
            colector.setPropiedadesDispositivo(innerPropertiesList);
            log.info("Colector {} en la fila {}", colector, currentRow.getRowNum() + 1);
            colectoresObj.add((DispositivoComunicacionI) SerializationUtils.clone(colector));
        }

        Sheet medidoresDLMS = wb.getSheetAt(2);
        rowIt = medidoresDLMS.rowIterator();
        MedidorI medidor = new MedidorI();
        medidor.setEstado(Estado.ACTIVO.getValue());
        medidor.setFecha_estado(sdf.format(new Date()));
        medidor.setFecha_instalacion(sdf.format(new Date()));
        medidor.setFactor_multiplica(1F);
        medidor.setPropio(1);
        medidor.setTransf_corriente(1F);
        medidor.setNombre_tipo_disp("MONOFASICO 1F2H DIN DLMS V4D");
        List<PropiedadMedidorAsociada> listaPropiedadesMedidor = new ArrayList<>();
        PropiedadMedidorAsociada velocidad = new PropiedadMedidorAsociada();
        velocidad.setEstado(Estado.ACTIVO.getValue());
        velocidad.setNombre_estado("ACTIVO");
        velocidad.setNombre_prop_tipo_disp("velocidad");
        listaPropiedadesMedidor.add(velocidad);
        PropiedadMedidorAsociada clave = new PropiedadMedidorAsociada();
        clave.setEstado(Estado.ACTIVO.getValue());
        clave.setNombre_estado("ACTIVO");
        clave.setNombre_prop_tipo_disp("clave");
        listaPropiedadesMedidor.add(clave);
        PropiedadMedidorAsociada paridad = new PropiedadMedidorAsociada();
        paridad.setEstado(Estado.ACTIVO.getValue());
        paridad.setNombre_estado("ACTIVO");
        paridad.setNombre_prop_tipo_disp("paridad");
        listaPropiedadesMedidor.add(paridad);
        List<PropiedadMedidorAsociada> innerPropertiesMeterList;
        List<InsertDispositivoEnRedComunicacion> medidoresDLMSEnRedList = new ArrayList<>();
        InsertDispositivoEnRedComunicacion medidorDLMSEnRed;
        while(rowIt.hasNext()) {
            Row currentRow = rowIt.next();
            if (currentRow.getRowNum() == 0) {
                continue;
            }
            innerPropertiesMeterList = new ArrayList<>();
            medidorDLMSEnRed = new InsertDispositivoEnRedComunicacion();
            PropiedadMedidorAsociada innerProperty;
            String serialMedidorDLMS = "00" + getContent(currentRow.getCell(0));
            medidorDLMSEnRed.setEstado(Estado.ACTIVO.getValue());
            medidorDLMSEnRed.setId_tipo_red_com(22);
            medidorDLMSEnRed.setNivel_red(NivelRed.MEDIDOR.getValue());
            medidorDLMSEnRed.setSerial_padre("0013A200" + getContent(currentRow.getCell(4)));
            medidorDLMSEnRed.setSerial_dispositivo(serialMedidorDLMS);
            medidorDLMSEnRed.setUltimo_nivel(1);
            log.info("MedidorI DLMS en Red {}, fila {}", medidorDLMSEnRed, currentRow.getRowNum() + 1);
            dispositivosEnRedObj.add((InsertDispositivoEnRedComunicacion) SerializationUtils.clone(medidorDLMSEnRed));
            medidor.setSerial(serialMedidorDLMS);
            for(PropiedadMedidorAsociada property: listaPropiedadesMedidor) {
                property.setSerial_medidor(serialMedidorDLMS);
            }
            String velocidadStr = getContent(currentRow.getCell(1));
            innerProperty = listaPropiedadesMedidor.get(0);
            if(velocidadStr != null && !velocidadStr.isEmpty()) {
                innerProperty.setValue(velocidadStr);
            } else {
                innerProperty.setValue("9600");
            }

            String claveStr = getContent(currentRow.getCell(2));
            innerProperty = listaPropiedadesMedidor.get(1);
            if(claveStr != null && !claveStr.isEmpty()) {
                innerProperty.setValue(claveStr);
            } else {
                innerProperty.setValue("3132333435363738");
            }

            String paridadStr = getContent(currentRow.getCell(3));
            innerProperty = listaPropiedadesMedidor.get(2);
            if(paridadStr != null && !paridadStr.isEmpty()) {
                innerProperty.setValue(paridadStr);
            } else {
                innerProperty.setValue("N");
            }
            innerPropertiesMeterList.addAll(listaPropiedadesMedidor);
            medidor.setPropiedades(innerPropertiesMeterList);
            log.info("MedidorI DLMS {} en la fila {}", medidor, currentRow.getRowNum() + 1);
            medidoresObj.add((MedidorI) SerializationUtils.clone(medidor));
        }

        Sheet medidoresIDIS = wb.getSheetAt(3);
        rowIt = medidoresIDIS.rowIterator();
        MedidorI medidorIdis = new MedidorI();
        medidorIdis.setEstado(Estado.ACTIVO.getValue());
        medidorIdis.setFecha_estado(sdf.format(new Date()));
        medidorIdis.setFecha_instalacion(sdf.format(new Date()));
        medidorIdis.setFactor_multiplica(1F);
        medidorIdis.setPropio(1);
        medidorIdis.setTransf_corriente(1F);
        medidorIdis.setNombre_tipo_disp("MONOFASICO 1F2H DIN IDIS V4D");
        listaPropiedadesMedidor = new ArrayList<>();
        PropiedadMedidorAsociada funcion = new PropiedadMedidorAsociada();
        funcion.setEstado(Estado.ACTIVO.getValue());
        funcion.setNombre_estado("ACTIVO");
        funcion.setNombre_prop_tipo_disp("funcion");
        listaPropiedadesMedidor.add(funcion);
        paridad = new PropiedadMedidorAsociada();
        paridad.setEstado(Estado.ACTIVO.getValue());
        paridad.setNombre_estado("ACTIVO");
        paridad.setNombre_prop_tipo_disp("paridad");
        listaPropiedadesMedidor.add(paridad);
        velocidad = new PropiedadMedidorAsociada();
        velocidad.setEstado(Estado.ACTIVO.getValue());
        velocidad.setNombre_estado("ACTIVO");
        velocidad.setNombre_prop_tipo_disp("velocidad");
        listaPropiedadesMedidor.add(velocidad);
        PropiedadMedidorAsociada fabricante = new PropiedadMedidorAsociada();
        fabricante.setEstado(Estado.ACTIVO.getValue());
        fabricante.setNombre_estado("ACTIVO");
        fabricante.setNombre_prop_tipo_disp("fabricante");
        listaPropiedadesMedidor.add(fabricante);
        PropiedadMedidorAsociada version = new PropiedadMedidorAsociada();
        version.setEstado(Estado.ACTIVO.getValue());
        version.setNombre_estado("ACTIVO");
        version.setNombre_prop_tipo_disp("version");
        listaPropiedadesMedidor.add(version);
        PropiedadMedidorAsociada tipoIdis = new PropiedadMedidorAsociada();
        tipoIdis.setEstado(Estado.ACTIVO.getValue());
        tipoIdis.setNombre_estado("ACTIVO");
        tipoIdis.setNombre_prop_tipo_disp("tipo_idis");
        listaPropiedadesMedidor.add(tipoIdis);
        PropiedadMedidorAsociada politica = new PropiedadMedidorAsociada();
        politica.setEstado(Estado.ACTIVO.getValue());
        politica.setNombre_estado("ACTIVO");
        politica.setNombre_prop_tipo_disp("politica");
        listaPropiedadesMedidor.add(politica);
        PropiedadMedidorAsociada llave = new PropiedadMedidorAsociada();
        llave.setEstado(Estado.ACTIVO.getValue());
        llave.setNombre_estado("ACTIVO");
        llave.setNombre_prop_tipo_disp("llave");
        listaPropiedadesMedidor.add(llave);
        PropiedadMedidorAsociada autenticacion = new PropiedadMedidorAsociada();
        autenticacion.setEstado(Estado.ACTIVO.getValue());
        autenticacion.setNombre_estado("ACTIVO");
        autenticacion.setNombre_prop_tipo_disp("autenticacion");
        listaPropiedadesMedidor.add(autenticacion);
        List<InsertDispositivoEnRedComunicacion> medidoresIDISEnRedList = new ArrayList<>();
        InsertDispositivoEnRedComunicacion medidorIDISEnRed;
        while(rowIt.hasNext()) {
            Row currentRow = rowIt.next();
            if (currentRow.getRowNum() == 0) {
                continue;
            }
            innerPropertiesMeterList = new ArrayList<>();
            medidorIDISEnRed = new InsertDispositivoEnRedComunicacion();
            PropiedadMedidorAsociada innerProperty;
            String serialMedidorIdis = "00" + getContent(currentRow.getCell(0));
            medidorIDISEnRed.setEstado(Estado.ACTIVO.getValue());
            medidorIDISEnRed.setId_tipo_red_com(22);
            medidorIDISEnRed.setNivel_red(NivelRed.MEDIDOR.getValue());
            medidorIDISEnRed.setSerial_padre("0013A200" + getContent(currentRow.getCell(10)));
            medidorIDISEnRed.setSerial_dispositivo(serialMedidorIdis);
            medidorIDISEnRed.setUltimo_nivel(1);
            log.info("MedidorI IDIS en Red {}, fila {}", medidorIDISEnRed, currentRow.getRowNum() + 1);
            dispositivosEnRedObj.add((InsertDispositivoEnRedComunicacion) SerializationUtils.clone(medidorIDISEnRed));
            medidorIdis.setSerial(serialMedidorIdis);
            for(PropiedadMedidorAsociada property: listaPropiedadesMedidor) {
                property.setSerial_medidor(serialMedidorIdis);
            }
            String funcionStr = getContent(currentRow.getCell(1));
            innerProperty = listaPropiedadesMedidor.get(0);
            if(funcionStr != null && !funcionStr.isEmpty()) {
                innerProperty.setValue(funcionStr);
            } else {
                innerProperty.setValue("3");
            }

            String paridadStr = getContent(currentRow.getCell(2));
            innerProperty = listaPropiedadesMedidor.get(1);
            if(paridadStr != null && !paridadStr.isEmpty()) {
                innerProperty.setValue(paridadStr);
            } else {
                innerProperty.setValue("N");
            }

            String velocidadStr = getContent(currentRow.getCell(3));
            innerProperty = listaPropiedadesMedidor.get(2);
            if(velocidadStr != null && !velocidadStr.isEmpty()) {
                innerProperty.setValue(velocidadStr);
            } else {
                innerProperty.setValue("9600");
            }

            String fabricanteStr = getContent(currentRow.getCell(4));
            innerProperty = listaPropiedadesMedidor.get(3);
            if (fabricanteStr != null && !fabricanteStr.isEmpty()) {
                innerProperty.setValue(fabricanteStr);
            } else {
                innerProperty.setValue("YTL");
            }

            String versionStr = getContent(currentRow.getCell(5));
            innerProperty = listaPropiedadesMedidor.get(4);
            if(versionStr != null && !versionStr.isEmpty()){
                innerProperty.setValue(versionStr);
            } else {
                innerProperty.setValue("DDS-EY/80A/V4D IDIS");
            }

            String tipoIdisStr = getContent(currentRow.getCell(6));
            innerProperty = listaPropiedadesMedidor.get(5);
            if (tipoIdisStr != null && !tipoIdisStr.isEmpty()){
                innerProperty.setValue(tipoIdisStr);
            } else {
                innerProperty.setValue("102");
            }

            String politicaStr = getContent(currentRow.getCell(7));
            innerProperty = listaPropiedadesMedidor.get(6);
            if (politicaStr != null && !politicaStr.isEmpty()) {
                innerProperty.setValue(politicaStr);
            } else {
                innerProperty.setValue("48");
            }

            String llaveStr = getContent(currentRow.getCell(8));
            innerProperty = listaPropiedadesMedidor.get(7);
            if(llaveStr != null && !llaveStr.isEmpty()) {
                innerProperty.setValue(llaveStr);
            } else {
                innerProperty.setValue("000102030405060708090A0B0C0D0E0F");
            }

            String autenticacionStr = getContent(currentRow.getCell(9));
            innerProperty = listaPropiedadesMedidor.get(8);
            if (autenticacionStr != null && !autenticacionStr.isEmpty()) {
                innerProperty.setValue(autenticacionStr);
            } else {
                innerProperty.setValue("D0D1D2D3D4D5D6D7D8D9DADBDCDDDEDF");
            }

            innerPropertiesMeterList.addAll(listaPropiedadesMedidor);
            medidorIdis.setPropiedades(innerPropertiesMeterList);
            log.info("MedidorI IDIS {} en la fila {}", medidorIdis, currentRow.getRowNum() + 1);
            medidoresObj.add((MedidorI) SerializationUtils.clone(medidorIdis));
        }
        dispositivoComunicacionService.insertarDispositivoComunicacionMasivo(concentradoresObj);
        dispositivoComunicacionService.insertarDispositivoComunicacionMasivo(colectoresObj);
        medidoresService.insertarMedidoresMasivo(medidoresObj);
        redComunicacionService.insertarRedComunicacionMasivo(dispositivosEnRedObj);
    }

    public String getContent(Cell c){
        if (c == null) {
            return ""; // Return an empty string for null cells
        }
        Object obj;
        switch(c.getCellType()){
            case BOOLEAN  :  {
                obj = c.getBooleanCellValue();
                break;
            }
            case ERROR    : {
                return ""+c.getErrorCellValue();
            }
            case FORMULA  : {
                return c.getCellFormula();
            }
            case NUMERIC  :  {
                if (DateUtil.isCellDateFormatted(c)) {
                    return c.getDateCellValue().toString(); // Format date as needed
                } else {
                    obj = c.getNumericCellValue();
                    break;
                }
            }
            case STRING   :  return c.getStringCellValue();

            default: return "";
        }
        return new BigDecimal(obj.toString()).toPlainString();
    }
}
