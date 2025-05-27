package com.TestAPI_AMS.API_AMS.domain.dto.Acciones.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ActionResponse {
    private String descripcion;
    private boolean error_respuesta;
    private Integer id_orden_insertada;
    private String mensaje_error_respuesta;
    private String numero_orden;
    private String serial;
    private List<Peticion> listaPeticiones; // Assuming it can be null
    private Object informacionConcentrador; // Assuming it can be null
    private Integer id_medidor; // Assuming it can be null
    private Integer id_disp_com; // Assuming it can be null
    private Integer id_proceso_lectura; // Assuming it can be null
    private Object origen; // Assuming it can be null
    private List<Object> listaPeticionesInsertar; // Assuming it can be null

}
