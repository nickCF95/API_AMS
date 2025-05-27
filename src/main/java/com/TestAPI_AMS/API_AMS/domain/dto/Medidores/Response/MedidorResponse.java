package com.TestAPI_AMS.API_AMS.domain.dto.Medidores.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MedidorResponse {
    private List<Medidor> lista;
    private Integer total;
}
