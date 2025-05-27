package com.TestAPI_AMS.API_AMS.domain.dto.Aprovisionamiento.Response.Insert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispositivoComunicacionResponseI {
    private Integer id;
    private Integer status;
    private String error;
    private String message;
}
